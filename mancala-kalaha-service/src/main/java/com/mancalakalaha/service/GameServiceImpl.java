package com.mancalakalaha.service;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.mapper.ModelMapper;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.model.GameFactory;
import com.mancalakalaha.repository.GameRepository;
import com.mancalakalaha.service.handler.MoveHandlerChain;
import com.mancalakalaha.service.handler.SowHandler;
import com.mancalakalaha.service.handler.UpdateHandler;
import com.mancalakalaha.service.handler.ValidationHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class GameServiceImpl implements GameService {

    private final GameConfig gameConfig;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameConfig gameConfig, GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.gameConfig = gameConfig;
    }

    @Override
    public GameDto initializeGame() {
        int stoneCount = gameConfig.getStoneCount();
        int pitCount = gameConfig.getPitCount();
        Game game = GameFactory.createGame(stoneCount, pitCount);
        saveGame(game);
        return mapToGameDto(game);
    }

    @Override
    public GameDto getGame(String gameId) {
        Game game = this.getGameById(gameId);
        return mapToGameDto(game);
    }

    @Override
    public void endGame(String gameId) {
        Game game = getGameById(gameId);
        game.setGameStatus(GameStatus.ENDED);
        saveGame(game);
    }

    @Override
    public GameDto move(MoveDto moveDto) {
        Game game = getGameById(moveDto.getGameId());

        MoveHandlerChain handlerChain = new MoveHandlerChain(Arrays.asList(
                new ValidationHandler(gameConfig),
                new SowHandler(gameConfig),
                new UpdateHandler(gameConfig)
        ));

        handlerChain.process(game, moveDto);
        saveGame(game);
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
}
