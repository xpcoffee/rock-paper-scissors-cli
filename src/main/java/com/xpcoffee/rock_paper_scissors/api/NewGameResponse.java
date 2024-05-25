package com.xpcoffee.rock_paper_scissors.api;

public class NewGameResponse {
    String gameId;

    public NewGameResponse(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
