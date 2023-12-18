package com.mancalakalaha.service;

import com.mancalakalaha.BaseMongoContainerIT;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameServiceIT extends BaseMongoContainerIT {

    @Autowired
    private GameService gameService;

    private TestHelper testHelper;

    @BeforeEach
    public void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void initializeGame_ShouldReturnValidGameDto() {
        // When
        GameDto gameDto = gameService.initializeGame();

        // Then
        assertNotNull(gameDto);
        assertNotNull(gameDto.getId());
        assertEquals(GameStatus.STARTED, gameDto.getGameStatus());
        assertNotNull(gameDto.getCurrentPlayer());
    }

    @Test
    void givenGame_WhenMove_ShouldUpdateGameDtoWithValidMove() {
        // Given
        GameDto initialGameDto = gameService.initializeGame();
        MoveDto moveDto = testHelper.getNextMove(initialGameDto.getId(), 0, Player.A);

        // When
        GameDto updatedGameDto = gameService.move(moveDto);

        // Then
        assertNotNull(updatedGameDto);
        assertNotNull(updatedGameDto.getId());
        assertEquals(initialGameDto.getCurrentPlayer(), updatedGameDto.getCurrentPlayer());
    }

    @Test
    void giveNoGame_WhenMove_ShouldThrowGameNotFoundExceptionForInvalidGameId() {
        // When // Then
        assertThrows(GameNotFoundException.class, () -> gameService.move(testHelper.getMoveDto()));
    }

}
