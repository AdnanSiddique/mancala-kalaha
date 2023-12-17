package com.mancalakalaha.controller;

import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;
import com.mancalakalaha.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<GameDto> startGame() {
        GameDto game = gameService.initializeGame();
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping("/move")
    public ResponseEntity<GameDto> makeMove(@RequestBody MoveDto move) {
        GameDto updatedGame = gameService.move(move);
        return new ResponseEntity<>(updatedGame, HttpStatus.CREATED);
    }

    @PostMapping("/end")
    public ResponseEntity<String> endGame(@RequestParam(value = "gameId") String gameId) {
        gameService.endGame(gameId);
        return new ResponseEntity<>(gameId, HttpStatus.OK);
    }

}
