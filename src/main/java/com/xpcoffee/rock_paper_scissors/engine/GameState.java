package com.xpcoffee.rock_paper_scissors.engine;

import java.util.ArrayList;
import java.util.Collection;

public class GameState {
   private Collection<GameAction> actions = new ArrayList<>();

    public Collection<GameAction> getActions() {
        return actions;
    }

    public void addAction(GameAction action) {
        actions.add(action);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "actions=" + actions +
                '}';
    }
}
