package com.juegodados.CanoBroockCesar.model.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Document(collection = "player")
public class PlayerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int userID;
    private String userName;
    private String password;
    private String role;
    private LocalDate date;
    private List<DiceEntity> dicerolls;


    public PlayerEntity() {
        this.dicerolls = new ArrayList<>();
    }

    public void setDate(LocalDate date) {
        this.date = LocalDate.now();
    }

    public void setUserName(String userName) {
        if (userName.isEmpty()) {
            this.userName = "ANONYMOUS";
        } else {
            this.userName = userName;
        }
    }
}
