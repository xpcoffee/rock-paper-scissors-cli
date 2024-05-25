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
        GameAction player1GameAction = switch (player1Action) {
            case "rock" -> GameAction.rock(player1);
            case "paper" -> GameAction.paper(player1);
            case "scissors" -> GameAction.scissors(player1);
            case null, default -> fail("Unexpected player action " + player1Action);
        };

        GameAction player2GameAction = switch (player2Action) {
            case "rock" -> GameAction.rock(player2);
            case "paper" -> GameAction.paper(player2);
            case "scissors" -> GameAction.scissors(player2);
            case null, default -> fail("Unexpected player action " + player1Action);
        };

        var engine = new Engine();
        var gameId = engine.newGame();
        var firstAction = engine.playAction(gameId, player1GameAction);
        assertEquals(firstAction.getResultType(), GameStatus.StatusType.PENDING);

        var result = engine.playAction(gameId, player2GameAction);

        switch (expectedResult) {
            case "draw" -> assertEquals(result.getResultType(), GameStatus.StatusType.DRAW);
            case "player1" -> {
                assertEquals(result.getResultType(), GameStatus.StatusType.WIN);
                assertEquals(result.getWinner(), player1);
            }
            case "player2" -> {
                assertEquals(result.getResultType(), GameStatus.StatusType.WIN);
                assertEquals(result.getWinner(), player2);
            }
            case null, default -> fail("Unexpected result " + expectedResult);
        }
    }

    @Test
    void engine_returnsAbandoned_onFirstAction() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        var result = engine.playAction(gameId, GameAction.abandon(player1));

        assertEquals(result.getResultType(), GameStatus.StatusType.ABANDONED);
        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, new GameAction(player2, GameAction.GameActionType.Rock))
        );
    }

    @Test
    void engine_returnsAbandoned_onSecondAction() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, GameAction.rock(player1));
        var result = engine.playAction(gameId, GameAction.abandon(player2));

        assertEquals(result.getResultType(), GameStatus.StatusType.ABANDONED);
        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, new GameAction(player2, GameAction.GameActionType.Rock))
        );
    }


    @Test
    void engine_throws_whenGameIsWin() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, GameAction.rock(player1));
        engine.playAction(gameId, GameAction.paper(player2));

        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, GameAction.paper(player2))
        );
    }

    @Test
    void engine_throws_whenGameIsDraw() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, GameAction.rock(player1));
        engine.playAction(gameId, GameAction.rock(player2));

        assertThrows(
                GameConcludedException.class,
                () -> engine.playAction(gameId, GameAction.paper(player2))
        );
    }

    @Test
    void engine_throws_whenPlayerPlaysTwice() throws GameException {
        var engine = new Engine();
        var gameId = engine.newGame();

        engine.playAction(gameId, GameAction.rock(player1));

        assertThrows(
                AlreadyPlayedException.class,
                () -> engine.playAction(gameId, GameAction.paper(new Player(player1.getName())))
        );
    }
}