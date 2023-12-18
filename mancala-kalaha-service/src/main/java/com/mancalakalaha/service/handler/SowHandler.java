package com.mancalakalaha.service.handler;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

import java.util.stream.IntStream;

public class SowHandler implements MoveHandler {

    private final int rightHouseIndex;
    private final int leftHouseIndex;
    private final int totalPits;

    public SowHandler(GameConfig gameConfig) {
        this.totalPits = gameConfig.getTotalPit();
        this.rightHouseIndex = gameConfig.getRightHouse();
        this.leftHouseIndex = gameConfig.getLeftHouse();
    }

    @Override
    public void handle(Game game, MoveDto moveDto) {
        int pitIndex = moveDto.getPitIndex();
        int stones = game.getPits()[pitIndex];
        game.getPits()[pitIndex] = 0;

        game.setCurrentPitIndex(pitIndex);
        IntStream.range(0, stones) .forEach(index -> sowAll(game, index == stones - 1));
    }

    private void sowAll(Game game, Boolean lastStone) {
        int currentPitIndex = game.getCurrentPitIndex() == totalPits ? 0 : game.getCurrentPitIndex() + 1;

        Player playerTurn = game.getCurrentPlayer();
        handleSowRightIndex(game, currentPitIndex, playerTurn);

        int targetPit = game.getPits()[currentPitIndex];
        if (isIncrementNeeded(lastStone, currentPitIndex, playerTurn)) {
            targetPit++;
            game.getPits()[currentPitIndex] = targetPit;
            return;
        }
        handleOppositePit(game, targetPit, currentPitIndex);
    }

    private void handleSowRightIndex(Game game, int currentPitIndex, Player playerTurn) {
        if ((currentPitIndex == rightHouseIndex && playerTurn == Player.B) ||
                (currentPitIndex == leftHouseIndex && playerTurn == Player.A)) {
            currentPitIndex = currentPitIndex == totalPits ? 0 : currentPitIndex % totalPits + 1;
        }
        game.setCurrentPitIndex(currentPitIndex);
    }

    private boolean isIncrementNeeded( Boolean lastStone, int currentPitIndex, Player playerTurn) {
        return !lastStone || currentPitIndex == rightHouseIndex || currentPitIndex == leftHouseIndex
                || (currentPitIndex < rightHouseIndex && playerTurn == Player.B)
                || (currentPitIndex > rightHouseIndex && playerTurn == Player.A);
    }

    private void handleOppositePit(Game game, int targetPit, int currentPitIndex) {
        int oppositeStones = game.getPits()[totalPits - currentPitIndex - 1];

        if (targetPit == 0 && oppositeStones != 0) {
            handleEmptyTargetPit(game, oppositeStones, currentPitIndex);
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
}