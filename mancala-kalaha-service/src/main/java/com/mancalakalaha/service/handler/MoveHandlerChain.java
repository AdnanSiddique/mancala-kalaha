package com.mancalakalaha.service.handler;

import com.mancalakalaha.dto.MoveDto;
import com.mancalakalaha.model.Game;

import java.util.List;

public class MoveHandlerChain {
    private final List<MoveHandler> handlers;

    public MoveHandlerChain(List<MoveHandler> handlers) {
        this.handlers = handlers;
    }

    public void process(Game game, MoveDto moveDto) {
        for (MoveHandler handler : handlers) {
            handler.handle(game, moveDto);
        }
    }
}
