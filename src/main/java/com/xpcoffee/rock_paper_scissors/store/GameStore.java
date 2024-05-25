package com.xpcoffee.rock_paper_scissors.store;

import com.xpcoffee.rock_paper_scissors.engine.GameState;

import java.util.HashMap;
import java.util.Map;

public class GameStore {
    private Map<String, GameState> games = new HashMap<>();

    public GameState getGameState(String gameId) throws GameNotFoundException {
        var gameState = games.get(gameId);
        if(gameState == null) {
            throw new GameNotFoundException(gameId);
        }
        return gameState;
    }

    public Map<String, GameState> getGames() {
        return games;
    }

    public void setGames(Map<String, GameState> games) {
        this.games = games;
    }
}
