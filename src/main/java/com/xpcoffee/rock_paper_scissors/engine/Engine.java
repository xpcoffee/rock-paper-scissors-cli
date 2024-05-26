package com.xpcoffee.rock_paper_scissors.engine;

import com.xpcoffee.rock_paper_scissors.engine.EngineGameAction.GameActionType;
import com.xpcoffee.rock_paper_scissors.GameException;
import com.xpcoffee.rock_paper_scissors.store.GameStore;

import java.util.UUID;

public class Engine {
    private GameStore store;

    public Engine(GameStore store) {
        this.store = store;
    }

    // For testing
    protected Engine() {
        store = new GameStore();
    }

    public String newGame() {
        var gameId = String.valueOf(UUID.randomUUID());
        store.getGames().put(gameId, new EngineGameState());
        return gameId;
    }

    public EngineGameStatus playAction(String gameId, EngineGameAction action) throws GameException {
        var gameState = store.getGameState(gameId);

        if (!EngineGameStatus.StatusType.PENDING.equals(determineStatus(gameState).getResultType())) {
            throw new GameConcludedException();
        }

        if (
                !gameState.getActions().isEmpty() &&
                        action.getPlayer().getName()
                                .equals(gameState.getActions().iterator().next().getPlayer().getName())
        ) {
            throw new AlreadyPlayedException();
        }

        gameState.addAction(action);
        return determineStatus(gameState);
    }

    public EngineGameStatus determineStatus(EngineGameState engineGameState) {
        var actions = engineGameState.getActions();

        if (!actions.isEmpty() && actions.stream().anyMatch(action -> action.getType() == GameActionType.Abandon)) {
            return EngineGameStatus.abandonned();
        }

        if (actions.size() < 2) {
            return EngineGameStatus.pending();
        }

        var iterator = actions.iterator();
        var action1 = iterator.next();
        var action2 = iterator.next();
        if (action1.getType() == action2.getType()) {
            return EngineGameStatus.draw();
        }

        // -- only win condition from here --

        var doesAction1Win = switch (action1.getType()) {
            case Rock -> action2.getType() == GameActionType.Scissors;
            case Paper -> action2.getType() == GameActionType.Rock;
            // Scissors (Java type-narrowing means we need default here)
            default -> action2.getType() == GameActionType.Paper;
        };

        return EngineGameStatus.winner(doesAction1Win ? action1.getPlayer() : action2.getPlayer());
    }
}
