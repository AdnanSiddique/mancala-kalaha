package com.mancalakalaha.service.handler;

import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.InvalidMoveException;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ValidationHandlerTest {

    private TestHelper testHelper;

    @BeforeEach
    private void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void handle_ValidMove_ShouldNotThrowException() {
        // Given
        ValidationHandler validationHandler = new ValidationHandler(testHelper.getGameConfig());

        Game game = testHelper.getGame();

        MoveDto moveDto = new MoveDto();
        moveDto.setPitIndex(2);

        // When & Then
        assertDoesNotThrow(() -> validationHandler.handle(game, moveDto));
    }

    @Test
    void handle_InvalidMove_ShouldThrowException() {
        // Given
        ValidationHandler validationHandler = new ValidationHandler(testHelper.getGameConfig());

        Game game = testHelper.getGame();

        MoveDto moveDto = new MoveDto();
        moveDto.setPitIndex(6); // Invalid move

        // When & Then
        assertThrows(InvalidMoveException.class, () -> validationHandler.handle(game, moveDto));
    }

}
