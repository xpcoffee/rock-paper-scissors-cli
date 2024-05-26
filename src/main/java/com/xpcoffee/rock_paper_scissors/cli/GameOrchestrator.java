package com.xpcoffee.rock_paper_scissors.cli;

import com.xpcoffee.rock_paper_scissors.adversary.Adversary;
import com.xpcoffee.rock_paper_scissors.engine.*;
import com.xpcoffee.rock_paper_scissors.store.GameStore;

/**
 * Holds the core logic that controls the flow of the game.
 */
public class GameOrchestrator {
    private static final GameStore store = new GameStore();
    private static final Engine engine = new Engine(store);
    private static final Adversary adversary = new Adversary(engine);

    // Potentially make this configurable later
    private static final int DEFAULT_GAMES_LEFT = 4;

    /**
     * The game loop
     */
    public static void play() throws Exception {
        Prompter.printTitle();
        int gamesLeft = DEFAULT_GAMES_LEFT;
        var player = new Player(Prompter.promptForName());

        while (gamesLeft > 0) {
            System.out.println("Games left: " + gamesLeft);
            gameFlow(player);
            gamesLeft --;
        }

        Prompter.printEnd();
    }

    /**
     * The sequence of steps per game
     */
    private static void gameFlow(Player player) throws Exception {
        // Start a new game & initiate the adversary
        var gameId = engine.newGame();
        adversary.playMove(gameId);

        // Player gets to play
        var action = Prompter.promptForAction();
        exitOnAbandon(action);
        engine.playAction(gameId, new EngineGameAction(player, action));

        // Wait for resolution
        var status = EngineGameStatus.pending();
        EngineGameState state = null;

        while (status.getResultType().equals(EngineGameStatus.StatusType.PENDING)) {
            state = store.getGameState(gameId);
            status = engine.determineStatus(state);
            Thread.sleep(100);
        }

        if (state == null) {
            throw new Exception("Unexpected condition: state is null after endgame");
        }
        Prompter.printResults(status, state);
    }


    private static void exitOnAbandon(EngineGameAction.GameActionType actionType) {
        if (actionType == EngineGameAction.GameActionType.Abandon) {
            System.out.println("Thanks for playing! Goodbye!");
            System.exit(0);
        }
    }
}
