package com.mancalakalaha.exception;

import com.mancalakalaha.constant.Player;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(Player player, int pitIndex){
        super(String.format("Invalid move for Player %s for pit %s", player, pitIndex));
    }
}
