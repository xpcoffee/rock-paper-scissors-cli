package com.xpcoffee.rock_paper_scissors.engine;

import java.util.ArrayList;
import java.util.Collection;

public class EngineGameState {
    private ArrayList<EngineGameAction> actions = new ArrayList<>();

    public EngineGameState() { }
    public EngineGameState(EngineGameState other) {
        this.actions = new ArrayList<>(other.actions);
    }

    public Collection<EngineGameAction> getActions() {
        return actions;
    }

    public void addAction(EngineGameAction action) {
        actions.add(action);
    }

    public EngineGameState obfuscate() {
        var obfuscatedState = new EngineGameState();
        obfuscatedState.addAction(actions.iterator().next().obfuscate());
        return obfuscatedState;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "actions=" + actions +
                '}';
    }
}
