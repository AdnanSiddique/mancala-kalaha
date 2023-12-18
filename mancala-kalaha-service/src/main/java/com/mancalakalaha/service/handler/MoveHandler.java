package com.mancalakalaha.service.handler;

import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

public interface MoveHandler {
    void handle(Game game, MoveDto moveDto);
}
