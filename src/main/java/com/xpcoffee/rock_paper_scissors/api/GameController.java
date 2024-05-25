package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.GameException;
import com.xpcoffee.rock_paper_scissors.engine.Engine;
import com.xpcoffee.rock_paper_scissors.engine.GameAction;
import com.xpcoffee.rock_paper_scissors.engine.Player;
import com.xpcoffee.rock_paper_scissors.store.GameNotFoundException;
import com.xpcoffee.rock_paper_scissors.store.GameStore;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {
    private final Engine engine;
    private final GameStore store;

    public GameController(Engine engine, GameStore store) {
        this.engine = engine;
        this.store = store;
    }

    @GetMapping
    public GameList getGames() {
        var games = store.getGames().entrySet().stream().map((entry) -> {
            var gameId = entry.getKey();
            var gameState = entry.getValue();
            var gameStatus = engine.determineStatus(gameState);
            return new GameDetail(gameId, gameState, gameStatus);
        }).collect(Collectors.toSet());

        return new GameList(games);
    }

    @PutMapping
    public NewGameResponse newGame() {
        var gameId = engine.newGame();
        return new NewGameResponse(gameId);
    }

    @GetMapping("/{gameId}")
    public GameDetail getGame(@PathVariable String gameId) throws GameNotFoundException {
        var gameState = store.getGameState(gameId);
        var gameStatus = engine.determineStatus(gameState);
        return new GameDetail(gameId, gameState, gameStatus);
    }

    @PostMapping("/{gameId}")
    public PlayActionResponse playAction(@PathVariable String gameId, @RequestBody PlayActionRequest request) throws GameException {
        Player player = new Player(request.playerName);
        GameAction gameAction = switch(request.action) {
            case "rock" -> GameAction.rock(player);
            case "paper" -> GameAction.paper(player);
            case "scissors" -> GameAction.scissors(player);
            case null, default -> throw new BadRequestException();
        };
        return new PlayActionResponse(engine.playAction(gameId, gameAction));
    }
}
