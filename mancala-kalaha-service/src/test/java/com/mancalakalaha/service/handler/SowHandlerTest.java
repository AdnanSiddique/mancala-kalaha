package com.mancalakalaha.service.handler;

import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SowHandlerTest {

    private TestHelper testHelper;

    @BeforeEach
    private void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void handle_SowStones_SuccessfullySowsStones() {
        // Given
        SowHandler sowHandler = new SowHandler(testHelper.getGameConfig());
        Game game = testHelper.getGame();
        MoveDto moveDto = new MoveDto();
        moveDto.setPitIndex(1);

        // When
        sowHandler.handle(game, moveDto);

        //Then
        assertNotNull(game);
        assertEquals(game.getPits()[1] ,  0);
        assertEquals(game.getCurrentPlayer(), Player.A );

        int[] expectedPits = new int[]{4,0,5,5,5,5,4,4,4,4,4,4,4,4}; // Expected state after sowing
        assertArrayEquals(expectedPits, game.getPits());
    }
}
