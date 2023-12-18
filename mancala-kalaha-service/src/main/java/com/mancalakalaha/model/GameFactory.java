package com.mancalakalaha.model;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFactory {

    public static Game createGame(int stoneCount, int pitsCount) {
        int[] pits = initializePits(stoneCount, pitsCount);

        Player currentPlayer = Player.A;
        GameStatus gameStatus = GameStatus.STARTED;
        LocalDateTime createDateTime = LocalDateTime.now();
        List<Move> moves = new ArrayList<>();

        return new Game(pits, currentPlayer, gameStatus, createDateTime, moves);
    }

    private static int[] initializePits(int stoneCount, int pitsCount) {
        int totalPits = (pitsCount * 2) + 2;
        int[] pits = new int[totalPits];

        Arrays.fill(pits, 0, pitsCount, stoneCount);
        Arrays.fill(pits, pitsCount + 1, pitsCount * 2 + 1, stoneCount);

        return pits;
    }
}
