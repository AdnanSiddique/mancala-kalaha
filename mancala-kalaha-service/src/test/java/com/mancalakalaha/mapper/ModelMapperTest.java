package com.mancalakalaha.mapper;

import com.mancalakalaha.constant.GameStatus;
import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ModelMapperTest {

    private TestHelper testHelper;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() {
        modelMapper = Mappers.getMapper(ModelMapper.class);
        testHelper = TestHelper.getInstance();
    }

    @Test
    public void testGameDtoFromGame() {
        Game game = testHelper.getGame();
        GameDto gameDto = modelMapper.gameDtoFrom(game);

        assertNotNull(gameDto);
        assertNull(gameDto.getPits());
        assertEquals(gameDto.getGameStatus(), GameStatus.STARTED);
        assertEquals(gameDto.getCurrentPlayer(), game.getCurrentPlayer());
        assertEquals(gameDto.getId(), game.getId());
    }

    @Test
    public void testGameDtoFromNullGame() {
        GameDto gameDto = modelMapper.gameDtoFrom(null);
        assertNull(gameDto);
    }

}
