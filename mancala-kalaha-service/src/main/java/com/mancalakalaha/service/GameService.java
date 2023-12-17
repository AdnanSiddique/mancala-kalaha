package com.mancalakalaha.service;

import com.mancalakalaha.dto.GameDto;
import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

public interface GameService {

    GameDto initializeGame();
    GameDto getGame(String gameId);
    GameDto move(MoveDto moveDto);
    void endGame(String gameId);
}
