package com.mancalakalaha.utils;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.model.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class TestHelper {

    private static final TestHelper SINGLETON_INSTANCE = new TestHelper();

    private TestHelper() {
    }

    public static TestHelper getInstance() {
        return SINGLETON_INSTANCE;
    }

    public final String TEST_GAME_ID = "1598557758956598554";

    public MoveDto getMoveDto() {
        return MoveDto.builder()
                .player(Player.A)
                .gameId(TEST_GAME_ID)
                .pitIndex(5)
                .build();
    }

    public GameDto getGameDto() {
        return GameDto.builder()
                .id(TEST_GAME_ID)
                .gameStatus(GameStatus.STARTED)
                .moves(Collections.singletonList(null))
                .currentPlayer(Player.A)
                .build();
    }

    public Game getGame() {
        int[] pits = IntStream.range(0, 14).toArray();
        Arrays.fill(pits, 4);
        return Game.builder().id(TEST_GAME_ID)
                .gameStatus(GameStatus.STARTED)
                .moves(new ArrayList<Move>())
                .currentPlayer(Player.A)
                .pits(pits)
                .build();
    }

    public GameConfig getGameConfig() {
        GameConfig gameConfig = new GameConfig();
        gameConfig.setPitCount(4);
        gameConfig.setPitCount(4);
        return gameConfig;
    }

    public MoveDto getNextMove(String gameId, int pitIndex, Player player) {
        return MoveDto.builder().pitIndex(pitIndex).gameId(gameId).player(player).build();
    }

}
