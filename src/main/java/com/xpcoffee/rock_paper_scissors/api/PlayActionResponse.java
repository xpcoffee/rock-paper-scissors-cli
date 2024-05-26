package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.engine.EngineGameStatus;

public class PlayActionResponse {
    EngineGameStatus status;

    public PlayActionResponse(EngineGameStatus status) {
        this.status = status;
    }

    public EngineGameStatus getStatus() {
        return status;
    }

    public void setStatus(EngineGameStatus status) {
        this.status = status;
    }
}
