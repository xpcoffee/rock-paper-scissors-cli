package com.xpcoffee.rock_paper_scissors.engine;

public class EngineGameStatus {
    private StatusType resultType;
    private Player winner;

    private EngineGameStatus(StatusType resultType) {
        this.resultType = resultType;
    }

    public static EngineGameStatus winner(Player player) {
        var result = new EngineGameStatus(StatusType.WIN);
        result.winner = player;
        return result;
    }

    public static EngineGameStatus draw() {
        return new EngineGameStatus(StatusType.DRAW);
    }

    public static EngineGameStatus abandonned() {
        return new EngineGameStatus(StatusType.ABANDONED);
    }

    public static EngineGameStatus pending() {
        return new EngineGameStatus(StatusType.PENDING);
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
