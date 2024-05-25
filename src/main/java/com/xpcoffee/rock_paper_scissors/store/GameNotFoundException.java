package com.xpcoffee.rock_paper_scissors.store;

import com.xpcoffee.rock_paper_scissors.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameNotFoundException extends GameException {
    public GameNotFoundException(String gameId) {
        super("Game not found: " + gameId);
    }
}
