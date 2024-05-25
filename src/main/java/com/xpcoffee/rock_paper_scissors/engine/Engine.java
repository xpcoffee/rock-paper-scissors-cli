package com.xpcoffee.rock_paper_scissors.engine;

import com.xpcoffee.rock_paper_scissors.engine.GameAction.GameActionType;
import com.xpcoffee.rock_paper_scissors.GameException;
import com.xpcoffee.rock_paper_scissors.store.GameStore;

import java.util.UUID;
import java.util.logging.Logger;

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
        store.getGames().put(gameId, new GameState());
        return gameId;
    }

    public GameStatus playAction(String gameId, GameAction action) throws GameException {
        var gameState = store.getGameState(gameId);

        if (!GameStatus.StatusType.PENDING.equals(determineStatus(gameState).getResultType())) {
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

    public GameStatus determineStatus(GameState gameState) {
        var actions = gameState.getActions();

        if (!actions.isEmpty() && actions.stream().anyMatch(action -> action.getType() == GameActionType.Abandon)) {
            return GameStatus.abandonned();
        }

        if (actions.size() < 2) {
            return GameStatus.pending();
        }

        var iterator = actions.iterator();
        var action1 = iterator.next();
        var action2 = iterator.next();
        if (action1.getType() == action2.getType()) {
            return GameStatus.draw();
        }

        // -- only win condition from here --

        var doesAction1Win = switch (action1.getType()) {
            case Rock -> action2.getType() == GameActionType.Scissors;
            case Paper -> action2.getType() == GameActionType.Rock;
            // Scissors (Java type-narrowing means we need default here)
            default -> action2.getType() == GameActionType.Paper;
        };

        return GameStatus.winner(doesAction1Win ? action1.getPlayer() : action2.getPlayer());
    }
}
