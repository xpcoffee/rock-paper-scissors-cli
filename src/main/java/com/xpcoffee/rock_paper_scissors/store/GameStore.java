package com.xpcoffee.rock_paper_scissors.store;

import com.xpcoffee.rock_paper_scissors.engine.EngineGameState;

import java.util.HashMap;
import java.util.Map;

public class GameStore {
    private Map<String, EngineGameState> games = new HashMap<>();

    public EngineGameState getGameState(String gameId) throws GameNotFoundException {
        var gameState = games.get(gameId);
        if(gameState == null) {
            throw new GameNotFoundException(gameId);
        }
        return gameState;
    }

    public Map<String, EngineGameState> getGames() {
        return games;
    }

    public void setGames(Map<String, EngineGameState> games) {
        this.games = games;
    }
}
