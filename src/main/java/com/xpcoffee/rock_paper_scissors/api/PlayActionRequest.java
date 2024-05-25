package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.engine.GameAction;

public class PlayActionRequest {
    String playerName;
    String action;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}