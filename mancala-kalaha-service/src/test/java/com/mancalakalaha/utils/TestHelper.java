package com.mancalakalaha.utils;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

import java.util.Collections;

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
        return Game.builder().id(TEST_GAME_ID)
                .gameStatus(GameStatus.STARTED)
                .moves(Collections.singletonList(null))
                .currentPlayer(Player.A)
                .build();
    }

}
