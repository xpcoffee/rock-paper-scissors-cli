package com.xpcoffee.rock_paper_scissors.engine;

public class EngineGameAction {
    private final Player player;
    private final GameActionType actionType;

    public EngineGameAction(Player player, GameActionType actionType) {
        this.player = player;
        this.actionType = actionType;
    }

    public Player getPlayer() {
        return player;
    }

    public GameActionType getType() {
        return actionType;
    }

    public static EngineGameAction rock(Player player) {
        return new EngineGameAction(player, GameActionType.Rock);
    }

    public static EngineGameAction paper(Player player) {
        return new EngineGameAction(player, GameActionType.Paper);
    }

    public static EngineGameAction scissors(Player player) {
        return new EngineGameAction(player, GameActionType.Scissors);
    }

    public static EngineGameAction abandon(Player player) {
        return new EngineGameAction(player, GameActionType.Abandon);
    }

    public EngineGameAction obfuscate() {
        return new EngineGameAction(this.player, GameActionType.Hidden);
    }

    public enum GameActionType {
        Rock("rock"),
        Paper("paper"),
        Scissors("scissors"),
        Hidden("hidden"),
        Abandon("abandon");

        private  String value;
        GameActionType(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }
    }

    @Override
    public String toString() {
        return "GameAction{" +
                "player=" + player +
                ", actionType=" + actionType +
                '}';
    }
}
