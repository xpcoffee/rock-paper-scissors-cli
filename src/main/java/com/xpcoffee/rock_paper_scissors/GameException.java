package com.xpcoffee.rock_paper_scissors;

public class GameException extends RuntimeException {
    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }
}
