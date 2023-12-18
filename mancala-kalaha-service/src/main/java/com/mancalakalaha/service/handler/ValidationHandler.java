package com.mancalakalaha.service.handler;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.InvalidMoveException;
import com.mancalakalaha.model.Game;

public class ValidationHandler implements MoveHandler {

    private final int rightHouseIndex;
    private final int leftHouseIndex;

    public ValidationHandler(GameConfig gameConfig) {
        this.rightHouseIndex = gameConfig.getRightHouse();
        this.leftHouseIndex = gameConfig.getLeftHouse();
    }

    @Override
    public void handle(Game game, MoveDto moveDto) {
        int[] pits = game.getPits();
        int pitIndex = moveDto.getPitIndex();

        Player currentPlayer = game.getCurrentPlayer();
        if (!isValidMove(pitIndex, pits) || !isValidPlayerSowMove(pitIndex, currentPlayer)) {
            throw new InvalidMoveException(currentPlayer, pitIndex);
        }
    }

    private boolean isValidMove(int pitIndex, int[] pits) {
        return pitIndex != leftHouseIndex && pitIndex != rightHouseIndex && pits[pitIndex] != 0;
    }

    private boolean isValidPlayerSowMove(int pitIndex, Player currentPlayer) {
        return  switch (currentPlayer) {
            case A -> pitIndex < rightHouseIndex;
            case B -> pitIndex > rightHouseIndex;
        };
    }
}

