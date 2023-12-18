package com.mancalakalaha.mapper;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModelMapperIT {

    @Mock
    private ModelMapper mapper;

    private TestHelper testHelper;

    @BeforeEach
    public void init() {
        testHelper = TestHelper.getInstance();
    }

    @Test
    public void testGameDtoFromGameEntity() {
        when(mapper.gameDtoFrom(any(Game.class)))
                .thenReturn(testHelper.getGameDto());

        Game game = testHelper.getGame();

        GameDto gameDto = mapper.gameDtoFrom(testHelper.getGame());

        assertNotNull(gameDto);
        assertNull(gameDto.getPits());
        assertEquals(gameDto.getGameStatus(), GameStatus.STARTED);
        assertEquals(gameDto.getCurrentPlayer(), game.getCurrentPlayer());
        assertEquals(gameDto.getId(), game.getId());
        verify(mapper, times(1)).gameDtoFrom(any());
    }

}
