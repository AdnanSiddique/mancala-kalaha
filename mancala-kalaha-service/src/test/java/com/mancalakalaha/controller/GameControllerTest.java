package com.mancalakalaha.controller;

import com.mancalakalaha.utils.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameController gameController;

    private final TestHelper testHelper;

    public GameControllerTest(){
        testHelper = TestHelper.getInstance();
    }

    @Test
    public void startGameTest() {
        when(gameController.startGame()).thenReturn(new ResponseEntity<>(testHelper.getGameDto(), HttpStatus.CREATED));
        gameController.startGame();
        verify(gameController, times(1)).startGame();
    }

    @Test
    public void makeMoveTest() {
        when(gameController.makeMove(any())).thenReturn(new ResponseEntity<>(testHelper.getGameDto(), HttpStatus.CREATED));
        gameController.makeMove(testHelper.getMoveDto());
        verify(gameController, times(1)).makeMove(any());
    }

    @Test
    public void endGame() {
        when(gameController.endGame(any())).thenReturn(new ResponseEntity<>(testHelper.TEST_GAME_ID, HttpStatus.OK));
        gameController.endGame(testHelper.TEST_GAME_ID);
        verify(gameController, times(1)).endGame(any());
    }
}
