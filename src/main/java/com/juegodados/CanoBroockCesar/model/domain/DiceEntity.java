package com.juegodados.CanoBroockCesar.model.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class DiceEntity {

    private int roll1;
    private int roll2;
    private int matchResult;
    private String winner_looser;

    public DiceEntity() {
        this.roll1 = aleatoryNumber();
        this.roll2 = aleatoryNumber();
        this.matchResult = this.roll1 + this.roll2;
        this.winner_looser = winnerORlooser(this.matchResult);
    }

    public int aleatoryNumber() {
        return (int) (Math.random() * 6 + 1);
    }

    public String winnerORlooser(int matchResult) {
        String status;

        if (matchResult == 7) {
            status = "Winner";
        } else {
            status = "Looser";
        }
        return status;
    }
}
