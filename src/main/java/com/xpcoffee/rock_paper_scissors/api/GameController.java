package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.GameException;
import com.xpcoffee.rock_paper_scissors.adversary.Adversary;
import com.xpcoffee.rock_paper_scissors.api.generated.GameApi;
import com.xpcoffee.rock_paper_scissors.api.generated.model.*;
import com.xpcoffee.rock_paper_scissors.engine.*;
import com.xpcoffee.rock_paper_scissors.store.GameNotFoundException;
import com.xpcoffee.rock_paper_scissors.store.GameStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Provides a RESTful API for playing a game of rock-paper-scissors.
 */
@RestController
@RequestMapping("/game")
public class GameController implements GameApi {
    private final Engine engine;
    private final GameStore store;
    private final Adversary adversary;

    public GameController(Engine engine, GameStore store, Adversary adversary) {
        this.engine = engine;
        this.store = store;
        this.adversary = adversary;
    }

    @GetMapping
    public ResponseEntity<GameList> listGames() {
        var games = store.getGames().entrySet().stream().map((entry) -> {
            var gameId = entry.getKey();
            var gameState = entry.getValue();
            var gameStatus = engine.determineStatus(gameState);
            return new GameDetails(
                    gameId,
                    toExternalGameState(gameState),
                    toExternalGameStatus(gameStatus)
            );
        }).toList();

        return ResponseEntity.ok(new GameList(games));
    }

    @PutMapping
    public ResponseEntity<GameDetails> newGame(@RequestBody GameOptions options) {
        var gameId = engine.newGame();
        var gameState = store.getGameState(gameId);
        var gameStatus = engine.determineStatus(gameState);

        // Spawn adversaries
        if(options != null) {
            for (int i = options.getHumanPlayers(); i < 2; i++) {
                adversary.playMove(gameId);
            }
        }

        return ResponseEntity.ok(new GameDetails(
                gameId,
                toExternalGameState(gameState),
                toExternalGameStatus(gameStatus)
        ));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDetails> getGame(@PathVariable String gameId) throws GameNotFoundException {
        var gameState = store.getGameState(gameId);
        var gameStatus = engine.determineStatus(gameState);

        return ResponseEntity.ok(new GameDetails(
                gameId,
                toExternalGameState(gameState),
                toExternalGameStatus(gameStatus)
        ));
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<GameStatus> play(@PathVariable String gameId, @RequestBody GameAction request) throws GameException {
        Player player = new Player(request.getPlayerId());
        EngineGameAction engineGameAction = switch(request.getActionType()) {
            case ROCK -> EngineGameAction.rock(player);
            case PAPER -> EngineGameAction.paper(player);
            case SCISSORS -> EngineGameAction.scissors(player);
            case null, default -> throw new BadRequestException();
        };
        var status = toExternalGameStatus(engine.playAction(gameId, engineGameAction));
        return ResponseEntity.ok(status);
    }

    private GameAction toExternalGameAction(EngineGameAction engineGameAction) {
        GameAction.ActionTypeEnum actionType = switch (engineGameAction.getType()) {
            case Rock -> GameAction.ActionTypeEnum.ROCK;
            case Paper -> GameAction.ActionTypeEnum.PAPER;
            case Scissors -> GameAction.ActionTypeEnum.SCISSORS;
            case Hidden -> GameAction.ActionTypeEnum.HIDDEN;
            case Abandon -> GameAction.ActionTypeEnum.ABANDON;
        };

        var gameAction = new GameAction();
        gameAction.setActionType(actionType);
        gameAction.setPlayerId(engineGameAction.getPlayer().getName());
        return gameAction;
    }

    private GameState toExternalGameState(EngineGameState engineGameState) {
        var actions = engineGameState.getActions().stream().map(action -> {
            var shouldObfuscate = engineGameState.getActions().size() == 1;
            return toExternalGameAction(shouldObfuscate ? action.obfuscate() : action);
        }).toList();
        return new GameState(actions);
    }

    private GameStatus toExternalGameStatus(EngineGameStatus engineGameStatus) {
        return switch (engineGameStatus.getResultType()) {
            case WIN -> new GameStatus(GameStatus.StatusTypeEnum.WIN).winnerPlayerId(engineGameStatus.getWinner().getName());
            case DRAW -> new GameStatus(GameStatus.StatusTypeEnum.DRAW);
            case ABANDONED -> new GameStatus(GameStatus.StatusTypeEnum.ABANDONED);
            case PENDING -> new GameStatus(GameStatus.StatusTypeEnum.PENDING);
        };
    }
}
