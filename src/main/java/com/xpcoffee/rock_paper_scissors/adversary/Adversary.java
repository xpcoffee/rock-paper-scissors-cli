package com.xpcoffee.rock_paper_scissors.adversary;

import com.xpcoffee.rock_paper_scissors.engine.Engine;
import com.xpcoffee.rock_paper_scissors.engine.EngineGameAction;
import com.xpcoffee.rock_paper_scissors.engine.EngineGameAction.GameActionType;
import com.xpcoffee.rock_paper_scissors.engine.EngineGameStatus;
import com.xpcoffee.rock_paper_scissors.engine.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Simulates a computer adversary
 */
public class Adversary {
    private final List<GameActionType> allowedActions = Arrays.asList(GameActionType.Rock, GameActionType.Paper,GameActionType.Scissors);

    private final Engine engine;
    private final Random rng = new Random();


    public Adversary(Engine engine) {
        this.engine = engine;
    }

    public CompletableFuture<EngineGameStatus> playMove(String gameId) {
        var randomId = UUID.randomUUID();
        var adversaryPlayer = new Player("computer-adversary-" + randomId);

        return CompletableFuture.supplyAsync(
            () -> {
                var actionType = allowedActions.get(rng.nextInt(allowedActions.size()));
                return new EngineGameAction(adversaryPlayer, actionType);
            }
        ).thenApply(action -> engine.playAction(gameId, action));
    }
}
