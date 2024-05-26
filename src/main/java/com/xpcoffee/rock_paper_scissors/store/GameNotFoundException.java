package com.xpcoffee.rock_paper_scissors.store;

import com.xpcoffee.rock_paper_scissors.GameException;

public class GameNotFoundException extends GameException {
    public GameNotFoundException(String gameId) {
        super("Game not found: " + gameId);
    }
}
