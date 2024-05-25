package com.xpcoffee.rock_paper_scissors.api;

import java.util.Collection;

public class GameList {
    Collection<GameDetail> games;

    public GameList(Collection<GameDetail> games) {
        this.games = games;
    }

    public Collection<GameDetail> getGames() {
        return games;
    }

    public void setGames(Collection<GameDetail> games) {
        this.games = games;
    }
}
