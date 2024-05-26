package com.xpcoffee.rock_paper_scissors.cli;

import com.xpcoffee.rock_paper_scissors.engine.EngineGameAction;
import com.xpcoffee.rock_paper_scissors.engine.EngineGameState;
import com.xpcoffee.rock_paper_scissors.engine.EngineGameStatus;

import java.util.Scanner;

/**
 * Concerns with displaying text and asking the user for input
 */
public class Prompter {
    private static Scanner scanner = new Scanner(System.in);

   public static void printTitle() {
        System.out.println("-------------------");
        System.out.println("Rock-Paper-Scissors");
        System.out.println("-------------------");
        System.out.println();
    }

    public static void printEnd() {
        System.out.println("-- No more games! Thanks for playing! --");
    }

    public static String promptForName() {
        System.out.print("Please enter your name: ");
        var result = scanner.nextLine();
        System.out.println();
        return result;
    }

    public static EngineGameAction.GameActionType promptForAction() throws Exception {
        System.out.println("┌- Possible moves");
        System.out.println("| 1. rock");
        System.out.println("| 2. paper");
        System.out.println("| 3. scissors");
        System.out.println("└ 0. exit");
        System.out.print("Pick a move: ");
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

    public static void printResults(EngineGameStatus status, EngineGameState state) throws Exception {
        System.out.println();
        System.out.println("┌- Game Results");

        state.getActions().forEach(action -> {
            System.out.println("├┬ Player:" + action.getPlayer().getName());
            System.out.println("|└ Action:" + action.getType().toString());
        });

        switch (status.getResultType()) {
            case DRAW -> System.out.println("└ The game is a draw!");
            case WIN -> System.out.println("└ Winner: " + status.getWinner().getName());
            default -> throw new Exception("Unexpected end status: " + status.getResultType().toString());
        }

        System.out.println("-- Press <ENTER> to continue --");
        scanner.nextLine();
    }
}
