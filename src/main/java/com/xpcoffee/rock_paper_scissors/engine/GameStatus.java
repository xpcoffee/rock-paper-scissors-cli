package com.xpcoffee.rock_paper_scissors.engine;

public class GameStatus {
    private StatusType resultType;
    private Player winner;

    private GameStatus(StatusType resultType) {
        this.resultType = resultType;
    }

    public static GameStatus winner(Player player) {
        var result = new GameStatus(StatusType.WIN);
        result.winner = player;
        return result;
    }

    public static GameStatus draw() {
        return new GameStatus(StatusType.DRAW);
    }

    public static GameStatus abandonned() {
        return new GameStatus(StatusType.ABANDONED);
    }

    public static GameStatus pending() {
        return new GameStatus(StatusType.PENDING);
    }

    public StatusType getResultType() {
        return resultType;
    }

    public Player getWinner() {
        return winner;
    }

    public enum StatusType {
        PENDING("pending"),
        WIN("win"),
        DRAW("draw"),
        ABANDONED("abandoned");

        private String value;

        StatusType(String value) {
            this.value = value;
        }
    }
}
