package com.mancalakalaha.service.handler;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UpdateHandlerTest {

    private TestHelper testHelper;

    @BeforeEach
    private void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void handle_UpdatePlayerTurn_SuccessfullyUpdatesPlayerTurn() {
        // Given

        UpdateHandler updateHandler = new UpdateHandler(testHelper.getGameConfig());

        MoveDto moveDto = new MoveDto();
        moveDto.setGameId(UUID.randomUUID().toString());
        moveDto.setPlayer(Player.A);
        moveDto.setPitIndex(3);

        Game game = testHelper.getGame();

        // When
        updateHandler.handle(game, moveDto);

        // Then
        assertEquals(Player.B, game.getCurrentPlayer());
    }

    @Test
    void handle_UpdateGameStatusIfCompleted_SuccessfullyUpdatesGameStatus() {
        // Given
        UpdateHandler updateHandler = new UpdateHandler(testHelper.getGameConfig());

        Game game = new Game();
        game.setPits(new int[]{0, 0, 0, 0, 15, 0, 0, 0, 1, 11}); // Player B has one stone remaining
        game.setMoves(new ArrayList<>());

        MoveDto moveDto = new MoveDto();
        moveDto.setGameId(UUID.randomUUID().toString());
        moveDto.setPlayer(Player.B);
        moveDto.setPitIndex(8);

        // When
        updateHandler.handle(game, moveDto);

        // Then
        assertEquals(GameStatus.COMPLETED, game.getGameStatus());
    }
}
