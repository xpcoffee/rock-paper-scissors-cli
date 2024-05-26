package com.xpcoffee.rock_paper_scissors.engine;

import com.xpcoffee.rock_paper_scissors.GameException;

public class GameConcludedException extends GameException {
    public GameConcludedException() {
        super("Game already concluded.");
    }
}
