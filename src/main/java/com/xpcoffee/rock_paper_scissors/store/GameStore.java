package com.xpcoffee.rock_paper_scissors.store;

import com.xpcoffee.rock_paper_scissors.engine.EngineGameState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameStore {
    private Map<String, EngineGameState> games = new ConcurrentHashMap<>();

    public EngineGameState getGameState(String gameId) throws GameNotFoundException {
        var gameState = games.get(gameId);
        if(gameState == null) {
            throw new GameNotFoundException(gameId);
        }
        // create a copy to prevent mutation via side effects
        return new EngineGameState(gameState);
    }

    public void setGameState(String gameId, EngineGameState state) throws GameNotFoundException {
        if(!games.containsKey(gameId)) {
            throw new GameNotFoundException(gameId);
        }
        games.put(gameId, state);
    }

    public Map<String, EngineGameState> getGames() {
        return games;
    }

    public void setGames(Map<String, EngineGameState> games) {
        this.games = games;
    }
}
