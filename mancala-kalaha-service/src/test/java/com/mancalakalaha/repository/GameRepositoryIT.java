package com.mancalakalaha.repository;

import com.mancalakalaha.BaseMongoContainerIT;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class GameRepositoryIT extends BaseMongoContainerIT {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testSaveGame() {
        // Given
        Game game = new Game();
        game.setCurrentPlayer(Player.A);

        // When
        Game savedGame = gameRepository.save(game);

        // Then
        assertNotNull(savedGame.getId());
        assertEquals(game.getCurrentPlayer(), savedGame.getCurrentPlayer());
    }

    @Test
    public void testFindById() {
        // Given
        Game game = new Game();
        game.setCurrentPlayer(Player.B);
        Game savedGame = gameRepository.save(game);

        // When
        Game foundGame = gameRepository.findById(savedGame.getId()).orElse(null);

        // Then
        assertNotNull(foundGame);
        assertEquals(savedGame.getId(), foundGame.getId());
        assertEquals(savedGame.getCurrentPlayer(), foundGame.getCurrentPlayer());
    }

}
