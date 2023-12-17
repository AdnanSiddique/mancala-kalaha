package com.mancalakalaha.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

import java.util.Collections;

public class TestHelper {

    private static final TestHelper INSTANCE = new TestHelper();
    private final int pitCount = 6;
    private final int stoneCount = 6;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static TestHelper getInstance() {
        return INSTANCE;
    }

    private TestHelper() {
    }

    public final String TEST_GAME_ID = "1598557758956598554";

    public MoveDto getMoveDto() {
        return MoveDto.builder().player(Player.A).gameId(TEST_GAME_ID).pitIndex(5).build();
    }

    public GameDto getGameDto() {
        return GameDto.builder().id(TEST_GAME_ID)
                                .gameStatus(GameStatus.STARTED)
                                .moves(Collections.singletonList(null))
                                .currentPlayer(Player.A).build();
    }

    public Game getGame() {
        return Game.builder().id(TEST_GAME_ID)
                .gameStatus(GameStatus.STARTED)
                .moves(Collections.singletonList(null))
                .currentPlayer(Player.A).build();
    }

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
