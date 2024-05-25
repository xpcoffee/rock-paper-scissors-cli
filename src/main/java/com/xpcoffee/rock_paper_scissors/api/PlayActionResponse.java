package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.engine.GameStatus;

public class PlayActionResponse {
    GameStatus status;

    public PlayActionResponse(GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
