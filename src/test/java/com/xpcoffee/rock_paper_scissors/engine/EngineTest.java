package com.xpcoffee.rock_paper_scissors.engine;

import com.xpcoffee.rock_paper_scissors.GameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    @ParameterizedTest
    @CsvSource({
            "rock, rock, draw",
            "paper, paper, draw",
            "scissors, scissors, draw",
            "rock, scissors, player1",
            "rock, paper, player2",
            "scissors, paper, player1",
    })
    void engine_playAction_returnsWin(String player1Action, String player2Action, String expectedResult) throws GameException {
        EngineGameAction player1EngineGameAction = switch (player1Action) {
            case "rock" -> EngineGameAction.rock(player1);
            case "paper" -> EngineGameAction.paper(player1);
            case "scissors" -> EngineGameAction.scissors(player1);
            case null, default -> fail("Unexpected player action " + player1Action);
        };

        EngineGameAction player2EngineGameAction = switch (player2Action) {
            case "rock" -> EngineGameAction.rock(player2);
            case "paper" -> EngineGameAction.paper(player2);
            case "scissors" -> EngineGameAction.scissors(player2);
            case null, default -> fail("Unexpected player action " + player1Action);
        };

        var engine = new Engine();
        var gameId = engine.newGame();
        var firstAction = engine.playAction(gameId, player1EngineGameAction);
        assertEquals(firstAction.getResultType(), EngineGameStatus.StatusType.PENDING);

        var result = engine.playAction(gameId, player2EngineGameAction);

        switch (expectedResult) {
            case "draw" -> assertEquals(result.getResultType(), EngineGameStatus.StatusType.DRAW);
            case "player1" -> {
                assertEquals(result.getResultType(), EngineGameStatus.StatusType.WIN);
                assertEquals(result.getWinner(), player1);
            }
            case "player2" -> {
                assertEquals(result.getResultType(), EngineGameStatus.StatusType.WIN);
                assertEquals(result.getWinner(), player2);
            }
            case null, default -> fail("Unexpected result " + expectedResult);
        }
    }

    @Test
    void engine_returnsAbandoned_onFirstAction() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        var result = engine.playAction(gameId, EngineGameAction.abandon(player1));

        assertEquals(result.getResultType(), EngineGameStatus.StatusType.ABANDONED);
        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, new EngineGameAction(player2, EngineGameAction.GameActionType.Rock))
        );
    }

    @Test
    void engine_returnsAbandoned_onSecondAction() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, EngineGameAction.rock(player1));
        var result = engine.playAction(gameId, EngineGameAction.abandon(player2));

        assertEquals(result.getResultType(), EngineGameStatus.StatusType.ABANDONED);
        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, new EngineGameAction(player2, EngineGameAction.GameActionType.Rock))
        );
    }


    @Test
    void engine_throws_whenGameIsWin() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, EngineGameAction.rock(player1));
        engine.playAction(gameId, EngineGameAction.paper(player2));

        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, EngineGameAction.paper(player2))
        );
    }

    @Test
    void engine_throws_whenGameIsDraw() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, EngineGameAction.rock(player1));
        engine.playAction(gameId, EngineGameAction.rock(player2));

        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, EngineGameAction.paper(player2))
        );
    }

    @Test
    void engine_throws_whenPlayerPlaysTwice() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, EngineGameAction.rock(player1));

        assertThrows(
                AlreadyPlayedException.class,
                () -> engine.playAction(gameId, EngineGameAction.paper(new Player(player1.getName())))
        );
    }
}