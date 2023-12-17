package com.mancalakalaha.service;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.exception.InvalidMoveException;
import com.mancalakalaha.mapper.ModelMapper;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.model.Move;
import com.mancalakalaha.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.IntStream;


@Service
public class GameServiceImpl implements GameService {

    private final GameConfig gameConfig;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    private final int totalPits;
    private final int rightHouseIndex;
    private final int leftHouseIndex;

    public GameServiceImpl(GameConfig gameConfig, GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameConfig = gameConfig;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.totalPits = gameConfig.getTotalPit();
        this.rightHouseIndex = gameConfig.getRightHouse();
        this.leftHouseIndex = gameConfig.getLeftHouse();
    }

    @Override
    public GameDto initializeGame() {
        final Game game = createNewGame();
        saveGame(game);
        return mapToGameDto(game);
    }

    @Override
    public GameDto getGame(String gameId) {
        Game game = this.getGameById(gameId);
        return mapToGameDto(game);
    }

    private Game createNewGame() {
        int stoneCount = gameConfig.getStoneCount();
        int pitCount = gameConfig.getPitCount();
        return new Game(stoneCount, pitCount);
    }

    @Override
    public void endGame(String gameId) {
        Game game = getGameById(gameId);
        game.setGameStatus(GameStatus.ENDED);
        gameRepository.save(game);
    }

    @Override
    public GameDto move(MoveDto moveDto) {
        Game game = getGameById(moveDto.getGameId());

        if (validateSowRequest(game, moveDto.getPitIndex())) {
            sowAndUpdateGame(game, moveDto);
        }

        return mapToGameDto(game);
    }

    private GameDto mapToGameDto(Game game) {
        return modelMapper.gameDtoFrom(game);
    }

    private void saveGame(Game game) {
        gameRepository.save(game);
    }

    private Game getGameById(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    private boolean validateSowRequest(Game game, int pitIndex) {
        int[] pits = game.getPits();
        Player currentPlayer = game.getCurrentPlayer();

        return isValidMove(pitIndex, pits) && isValidPlayerSowMove(pitIndex, currentPlayer);
    }

    private boolean isValidMove(int pitIndex, int[] pits) {
        return pitIndex != leftHouseIndex && pitIndex != rightHouseIndex && pits[pitIndex] != 0;
    }

    private boolean isValidPlayerSowMove(int pitIndex, Player currentPlayer) {
        return  switch (currentPlayer) {
            case A -> pitIndex < rightHouseIndex;
            case B -> pitIndex > rightHouseIndex;
            default -> throw new InvalidMoveException(currentPlayer, pitIndex);
        };
    }

    private void sowAndUpdateGame(final Game game, final MoveDto move) {
        int pitIndex = move.getPitIndex();
        int stones = game.getPits()[pitIndex];
        game.getPits()[pitIndex] = 0;

        game.setCurrentPitIndex(pitIndex);
        IntStream.range(0, stones - 1).forEach(index -> sowToRight(game, false));
        sowToRight(game, true);

        int currentPitIndex = game.getCurrentPitIndex();
        updateGameAfterSow(game, currentPitIndex, move);
    }

    private void sowToRight(Game game, Boolean lastStone) {
        int currentPitIndex = game.getCurrentPitIndex() == totalPits ? 0 : game.getCurrentPitIndex() + 1;

        Player playerTurn = game.getCurrentPlayer();
        handleSowRightIndex(game, currentPitIndex, playerTurn);

        int targetPit = game.getPits()[currentPitIndex];
        if (shouldIncrementPit(lastStone, currentPitIndex, playerTurn)) {
            targetPit++;
            game.getPits()[currentPitIndex] = targetPit;
            return;
        }
        handleOppositePit(game, targetPit, currentPitIndex);
    }

    private void updateGameAfterSow(Game game, int currentPitIndex, MoveDto move) {
        if (currentPitIndex != rightHouseIndex && currentPitIndex != leftHouseIndex) {
            game.setNextPlayer();
        }

        LocalDateTime now = LocalDateTime.now();
        game.setIndividualMove(generateMoveRecord(move.getGameId(), move.getPlayer(), move.getPitIndex(), now));
        game.setUpdateDateTime(now);
        saveGame(game);
    }

    private void handleSowRightIndex(Game game, int currentPitIndex, Player playerTurn) {
        if ((currentPitIndex == rightHouseIndex && playerTurn == Player.B) ||
                (currentPitIndex == leftHouseIndex && playerTurn == Player.A)) {
            currentPitIndex = currentPitIndex == totalPits ? 0 : currentPitIndex % totalPits + 1;
        }
        game.setCurrentPitIndex(currentPitIndex);
    }

    private boolean shouldIncrementPit( Boolean lastStone, int currentPitIndex, Player playerTurn) {
        return !lastStone || currentPitIndex == rightHouseIndex || currentPitIndex == leftHouseIndex
                || (currentPitIndex < rightHouseIndex && playerTurn == Player.B)
                || (currentPitIndex > rightHouseIndex && playerTurn == Player.A);
    }

    private void handleOppositePit(Game game, int targetPit, int currentPitIndex) {
        int oppositePit = game.getPits()[totalPits - currentPitIndex - 1];

        if (targetPit == 0 && oppositePit != 0) {
            handleEmptyTargetPit(game, oppositePit, currentPitIndex);
            return;
        }

        targetPit++;
        game.getPits()[currentPitIndex] = targetPit;
    }

    private void handleEmptyTargetPit(Game game, int oppositeStones, int currentPitIndex) {
        game.getPits()[totalPits - currentPitIndex - 1] = 0;
        int pitHouseIndex = currentPitIndex < rightHouseIndex ? rightHouseIndex : leftHouseIndex;
        game.getPits()[pitHouseIndex] += (oppositeStones + 1);
    }

    private Move generateMoveRecord(String gameId, Player player, int pitIndex, LocalDateTime now) {
        return Move.builder()
                .id(UUID.randomUUID().toString())
                .gameId(gameId)
                .pitIndex(pitIndex)
                .player(player)
                .createDateTime(now)
                .build();
    }
}
