package com.mancalakalaha.service.handler;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.model.Move;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class UpdateHandler implements MoveHandler {

    private final int rightHouseIndex;
    private final int leftHouseIndex;
    private final int totalPits;

    public UpdateHandler(GameConfig gameConfig) {
        this.totalPits = gameConfig.getTotalPit();
        this.rightHouseIndex = gameConfig.getRightHouse();
        this.leftHouseIndex = gameConfig.getLeftHouse();
    }
    @Override
    public void handle(Game game, MoveDto moveDto) {
        updatePlayerTurn(game, game.getCurrentPitIndex());
        updateGameStatusIfCompleted(game);
        updateGame(game, moveDto);
    }

    private void updatePlayerTurn(Game game, int currentPitIndex) {
        if (currentPitIndex != rightHouseIndex && currentPitIndex != leftHouseIndex) {
            game.setNextPlayer();
        }
    }

    private void updateGameStatusIfCompleted(Game game) {
        int countStonesPlayerA = countStonesInPits(game, 0, rightHouseIndex - 1);
        int countStonesPlayerB = countStonesInPits(game, rightHouseIndex + 1, totalPits - 1);

        if (countStonesPlayerA == 0 || countStonesPlayerB == 0) {
            game.getPits()[rightHouseIndex] += countStonesPlayerA;
            game.getPits()[leftHouseIndex] += countStonesPlayerB;
            game.setGameStatus(GameStatus.COMPLETED);
        }
    }

    private int countStonesInPits(Game game, int start, int end) {
        return Arrays.stream(game.getPits(), start, end + 1).sum();
    }

    private void updateGame(Game game, MoveDto move) {
        LocalDateTime now = LocalDateTime.now();
        game.setIndividualMove(generateMoveRecord(move.getGameId(), move.getPlayer(), move.getPitIndex(), now));
        game.setUpdateDateTime(now);
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