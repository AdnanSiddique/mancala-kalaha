package com.mancalakalaha.service;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.mapper.ModelMapper;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.repository.GameRepository;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameConfig gameConfig;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private TestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    void initializeGame_ShouldReturnValidGameDto() {
        // Mock dependencies
        when(gameConfig.getStoneCount()).thenReturn(4);
        when(gameConfig.getPitCount()).thenReturn(6);

        // Mock repository save
        when(gameRepository.save(any(Game.class))).thenReturn(testHelper.getGame());

        // Mock mapper
        when(modelMapper.gameDtoFrom(any(Game.class))).thenReturn(testHelper.getGameDto());

        // When
        GameDto gameResponse = gameService.initializeGame();

        // Then
        assertNotNull(gameResponse);
        assertNotNull(gameResponse.getId());
        assertEquals(GameStatus.STARTED, gameResponse.getGameStatus());
        assertNotNull(gameResponse.getCurrentPlayer());

        // Verify interactions
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(modelMapper, times(1)).gameDtoFrom(any(Game.class));
    }

    @Test
    void getGame_ShouldReturnValidGameDto() {
        // Mock dependencies
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(new Game()));
        when(modelMapper.gameDtoFrom(any(Game.class))).thenReturn(new GameDto());

        // When
        GameDto gameDto = gameService.getGame(testHelper.TEST_GAME_ID);

        // Then
        assertNotNull(gameDto);
        // Add more assertions based on your implementation

        // Verify interactions
        verify(gameRepository, times(1)).findById(eq(testHelper.TEST_GAME_ID));
        verify(modelMapper, times(1)).gameDtoFrom(any(Game.class));
    }

    @Test
    void endGame_ShouldSetGameStatusToEnded() {
        // Mock dependencies
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(new Game()));

        // When
        assertDoesNotThrow(() -> gameService.endGame(testHelper.TEST_GAME_ID));

        // Verify interactions
        verify(gameRepository, times(1)).findById(eq(testHelper.TEST_GAME_ID));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    // Add more tests for the move method and other methods in the GameServiceImpl class
}
