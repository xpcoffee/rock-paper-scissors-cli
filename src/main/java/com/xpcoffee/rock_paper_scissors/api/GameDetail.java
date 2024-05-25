package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.engine.GameState;
import com.xpcoffee.rock_paper_scissors.engine.GameStatus;

public class GameDetail {
    String id;
    GameState state;
    GameStatus status;

    public GameDetail(String id, GameState state, GameStatus status) {
        this.id = id;
        this.state = state;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
