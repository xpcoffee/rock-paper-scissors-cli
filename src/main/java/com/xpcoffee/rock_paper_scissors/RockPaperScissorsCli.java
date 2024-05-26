package com.xpcoffee.rock_paper_scissors;

import com.xpcoffee.rock_paper_scissors.adversary.Adversary;
import com.xpcoffee.rock_paper_scissors.engine.*;
import com.xpcoffee.rock_paper_scissors.store.GameStore;

import java.util.Scanner;

public class RockPaperScissorsCli {
    private static GameStore store = new GameStore();
    private static Engine engine = new Engine(store);
    private static Adversary adversary = new Adversary(engine);
    private static Scanner scanner = new Scanner(System.in);

    private static int gamesLeft = 4;

    public static void main(String[] args) throws Exception {
        printTitle();
        var player = new Player(promptForName());

        // game loop
        while (gamesLeft > 0) {
            System.out.println("Games left: " + gamesLeft);
            gameFlow(player);
            gamesLeft --;
        }

        printEnd();
    }

    private static void gameFlow(Player player) throws Exception {
        // Start a new game & initiate the adversary
        var gameId = engine.newGame();
        adversary.playMove(gameId);

        // Player gets to play
        var action = promptForAction();
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
            System.out.println("Unexpected condition: state is null after endgame");
            System.exit(1);
        }
        printResults(status, state);
    }

    private static void printTitle() {
        System.out.println("-------------------");
        System.out.println("Rock-Paper-Scissors");
        System.out.println("-------------------");
        System.out.println();
    }

    private static void printEnd() {
        System.out.println();
        System.out.println();
        System.out.println("No more games! Thanks for playing!");
        System.out.println("-------------------");
    }

    private static String promptForName() {
        System.out.print("Please enter your name: ");
        var result = scanner.nextLine();
        System.out.println();
        return result;
    }

    private static EngineGameAction.GameActionType promptForAction() throws Exception {
        System.out.println("┌- Possible moves");
        System.out.println("| 1. rock");
        System.out.println("| 2. paper");
        System.out.println("| 3. scissors");
        System.out.println("└ 0. exit");
        System.out.print("Pick a move:");
        Integer choice = null;

        while (choice == null) {
            var result = scanner.nextLine();
            try {
                var maybeChoice = Integer.parseInt(result);
                if (maybeChoice > 3 || maybeChoice < 0) {
                    throw new Exception("Incorrect input");
                }
                choice = maybeChoice;
            } catch (Exception e) {
                System.out.println("[Incorrect input] Please try again");
            }
        }

        return switch (choice) {
            case 1 -> EngineGameAction.GameActionType.Rock;
            case 2 -> EngineGameAction.GameActionType.Paper;
            case 3 -> EngineGameAction.GameActionType.Scissors;
            case 0 -> EngineGameAction.GameActionType.Abandon;
            default -> {
                System.out.println("Unexpected condition: " + choice);
                throw new Exception("Unexpected condition: " + choice);
            }
        };
    }

    private static void exitOnAbandon(EngineGameAction.GameActionType actionType) {
        if (actionType == EngineGameAction.GameActionType.Abandon) {
            System.out.println("Thanks for playing! Goodbye!");
            System.exit(0);
        }
    }

    private static void printResults(EngineGameStatus status, EngineGameState state) throws Exception {
        System.out.println();
        System.out.println("┌- Game Results");

        state.getActions().forEach(action -> {
            System.out.println("├┬ Player:" + action.getPlayer().getName());
            System.out.println("|└ Action:" + action.getType().toString());
        });

        System.out.println("|");
        switch (status.getResultType()) {
            case DRAW -> System.out.println("└ The game is a draw!");
            case WIN -> System.out.println("└ Winner: " + status.getWinner().getName());
            default -> throw new Exception("Unexpected end status: " + status.getResultType().toString());
        }

        System.out.println("-- Press <ENTER> to continue --");
        scanner.nextLine();
    }
}
