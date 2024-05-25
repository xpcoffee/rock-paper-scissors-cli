package com.xpcoffee.rock_paper_scissors.engine;

public class GameAction {
    private final Player player;
    private final GameActionType actionType;

    public GameAction(Player player, GameActionType actionType) {
        this.player = player;
        this.actionType = actionType;
    }

    public Player getPlayer() {
        return player;
    }

    public GameActionType getType() {
        return actionType;
    }

    public static GameAction rock(Player player) {
        return new GameAction(player, GameActionType.Rock);
    }

    public static GameAction paper(Player player) {
        return new GameAction(player, GameActionType.Paper);
    }

    public static GameAction scissors(Player player) {
        return new GameAction(player, GameActionType.Scissors);
    }

    public static GameAction abandon(Player player) {
        return new GameAction(player, GameActionType.Abandon);
    }

    public enum GameActionType {
        Rock("rock"),
        Paper("paper"),
        Scissors("scissors"),
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
