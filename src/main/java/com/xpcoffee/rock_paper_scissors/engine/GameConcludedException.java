package com.xpcoffee.rock_paper_scissors.engine;

import com.xpcoffee.rock_paper_scissors.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GameConcludedException extends GameException {
    public GameConcludedException() {
        super("Game already concluded.");
    }
}
