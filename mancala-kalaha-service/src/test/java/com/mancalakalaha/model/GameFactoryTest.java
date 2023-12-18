package com.mancalakalaha.model;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFactoryTest {

    @Test
    void createNewGame_ReturnsGameWithCorrectInitialization() {
        // Arrange
        int stoneCount = 6;
        int pitsCount = 6;

        // Act
        Game game = GameFactory.createGame(stoneCount, pitsCount);

        // Assert
        assertNotNull(game);
        assertEquals(GameStatus.STARTED, game.getGameStatus());
        assertEquals(Player.A, game.getCurrentPlayer());

        int[] pits = game.getPits();
        assertNotNull(pits);
        assertEquals((pitsCount * 2) + 2, pits.length);
    }
}
