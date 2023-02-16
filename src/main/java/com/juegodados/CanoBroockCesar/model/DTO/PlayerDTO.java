package com.juegodados.CanoBroockCesar.model.DTO;


import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlayerDTO implements Serializable, Comparable<PlayerDTO> {

    private static final long serialVersionUID = 1L;
    private int userID;
    private String userName;
    private LocalDate date;
    private float successRate;

    @Override
    public int compareTo(PlayerDTO o) {
        if(getSuccessRate()>o.getSuccessRate()) {
            return 0;
        }else if(getSuccessRate()<o.getSuccessRate()){
            return 1;
        }else{
            return -1;
        }
    }
}
