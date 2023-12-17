package com.mancalakalaha.service;

import com.mancalakalaha.config.GameConfig;
import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.constant.Player;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.exception.GameNotFoundException;
import com.mancalakalaha.mapper.ModelMapper;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.repository.GameRepository;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GameServiceIT {

    private GameServiceImpl gameService;

    @Mock
    private GameConfig gameConfig;
    @Mock
    private GameRepository gameRepository;

    @Mock
    private ModelMapper modelMapper;

    private TestHelper testHelper;

    @BeforeEach
    void init() {
        testHelper = TestHelper.getInstance();
        gameService = Mockito.spy(new GameServiceImpl(gameConfig, gameRepository, modelMapper));

        //Given
        when(gameConfig.getTotalPit()).thenReturn(14);
        when(gameConfig.getPitCount()).thenReturn(6);
        when(gameConfig.getStoneCount()).thenReturn(6);
        when(gameConfig.getLeftHouse()).thenReturn(6);
        when(gameConfig.getRightHouse()).thenReturn(13);
    }

    @Test
    public void testInitializeGame() {
        // When
        when(gameRepository.save(any(Game.class))).thenReturn(testHelper.getGame());
        when(modelMapper.gameDtoFrom(any(Game.class))).thenReturn(testHelper.getGameDto());
        GameDto gameDto = gameService.initializeGame();

        // Then
        assertNotNull(gameDto);
        assertNotNull(gameDto.getId());
        assertEquals(GameStatus.STARTED, gameDto.getGameStatus());
        assertNotNull(gameDto.getCurrentPlayer());
    }

    @Test
    public void testMove() {
        // Given
        when(gameRepository.save(any(Game.class))).thenReturn(testHelper.getGame());
        when(modelMapper.gameDtoFrom(any(Game.class))).thenReturn(testHelper.getGameDto());
        when(gameRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(testHelper.getGame()));

        GameDto initialGameDto = gameService.initializeGame();
        MoveDto moveDto = new MoveDto();
        moveDto.setGameId(initialGameDto.getId());
        moveDto.setPitIndex(0);
        moveDto.setPlayer(Player.A);

        // When
        GameDto updatedGameDto = gameService.move(moveDto);

        // Then
        assertNotNull(updatedGameDto);
        assertNotNull(updatedGameDto.getId());
        assertEquals(initialGameDto.getCurrentPlayer(), updatedGameDto.getCurrentPlayer());
    }

    @Test
    public void testInvalidGameId() {
        // Given
        when(gameRepository.save(any(Game.class))).thenReturn(testHelper.getGame());
        when(modelMapper.gameDtoFrom(any(Game.class))).thenReturn(testHelper.getGameDto());

        //When //Then
        assertThrows(GameNotFoundException.class, () -> gameService.move(testHelper.getMoveDto()));
    }

}
