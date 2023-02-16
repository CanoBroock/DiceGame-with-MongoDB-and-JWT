package com.juegodados.CanoBroockCesar.controller;

import com.juegodados.CanoBroockCesar.model.DTO.PlayerDTO;
import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import com.juegodados.CanoBroockCesar.model.services.GameServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//La anotación @RestController marca la clase como un controlador donde cada método devuelve un objeto
// de dominio en lugar de una vista
@RestController
//Se utiliza para asignar solicitudes web
@RequestMapping("/players")
public class GameController {

    //Esta anotación se aplica a campos, métodos de “setters” y constructores,
    // inyecta la dependencia del objeto implícitamente.
    @Autowired
    private GameServiceImplement gameServiceImplement;

    @PostMapping("/add")
    //La anotación @RequestBody sirve para leer el objeto que le pasamos
    //ResponseEntity es el objeto que recibimos con la respuesta del servidor, la cual definimos nosotros.
    public ResponseEntity<?> addPlayer(@RequestBody PlayerEntity playerEntity) {
        ResponseEntity<?> responseEntity = null;

        PlayerEntity savePlayer = gameServiceImplement.savePlayer(playerEntity);
        if (savePlayer != null) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(savePlayer);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Player already exist");
        }
        return responseEntity;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPlayers() {
        List<PlayerDTO> playerDTOS = gameServiceImplement.getAllPlayers();
        return new ResponseEntity<>(playerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<?> getOnePlayer(@PathVariable(value = "id") Integer playerId) {
        ResponseEntity<?> responseEntity = null;
        List<DiceEntity> players = gameServiceImplement.getOneDiceRoll(playerId);

        if (players == null) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(players);
        }
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@RequestBody PlayerEntity playerDetails, @PathVariable(value = "id") Integer playerId) {
        ResponseEntity<?> responseEntity = null;
        Optional<PlayerEntity> player = gameServiceImplement.getOnePlayer(playerId);

        if (!player.isPresent()) {
            responseEntity = ResponseEntity.notFound().build();
        }
        player.get().setUserName(playerDetails.getUserName());
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(gameServiceImplement.savePlayer(player.get()));
        return responseEntity;
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<?> deleteDices(@PathVariable(value = "id") Integer playerId) {
        ResponseEntity<?> responseEntity = null;
        if (!gameServiceImplement.getOnePlayer(playerId).isPresent()) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body("Player rolls have been removed");
        } else {
            gameServiceImplement.deleteDices(playerId);
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<?> diceRoll(@PathVariable(value = "id") Integer playerId) {
        ResponseEntity<?> responseEntity = null;
        DiceEntity diceEntity = gameServiceImplement.rollDice(playerId);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(diceEntity);
        return responseEntity;
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking() {
        ResponseEntity<?> responseEntity = null;
        String diceEntity = gameServiceImplement.getRanking();
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(diceEntity);
        return responseEntity;
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<?> getLoser() {
        ResponseEntity<?> responseEntity = null;
        PlayerDTO playerDTO = gameServiceImplement.getLoser();
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(playerDTO);
        return responseEntity;
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<?> getWinner() {
        ResponseEntity<?> responseEntity = null;
        PlayerDTO playerDTO = gameServiceImplement.getWinner();
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(playerDTO);
        return responseEntity;
    }


}
