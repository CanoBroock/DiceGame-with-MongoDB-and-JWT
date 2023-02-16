package com.juegodados.CanoBroockCesar.model.services;

import com.juegodados.CanoBroockCesar.model.DTO.PlayerDTO;
import com.juegodados.CanoBroockCesar.model.domain.DiceEntity;
import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import com.juegodados.CanoBroockCesar.model.repository.DiceRepository;
import com.juegodados.CanoBroockCesar.model.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GameServiceImplement implements GameService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DiceRepository diceRepository;

    private PlayerDTO convertEntityToDTO(PlayerEntity playerEntity) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUserID(playerEntity.getUserID());
        playerDTO.setUserName(playerEntity.getUserName());
        playerDTO.setDate(playerEntity.getDate());
        return playerDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerDTO> getAllPlayers() {
        List<PlayerEntity> playerslist = playerRepository.findAll();
        List<PlayerDTO> playersDTO = new ArrayList<>();

        for (int i = 0; i < playerslist.size(); i++) {
            PlayerDTO playerDTO = convertEntityToDTO(playerslist.get(i));
            playersDTO.add(playerDTO);
        }
        for (int i = 0; i < playerslist.size(); i++) {
            int winners = 0;
            float percentage = 0.0F;
            for (int k = 0; k < playerslist.get(i).getDicerolls().size(); k++) {
                if (playerslist.get(i).getDicerolls().get(k).getWinner_looser().equals("Winner")) {
                    winners++;
                }
                percentage = (winners * 100) / playerslist.get(i).getDicerolls().size();
            }
            playersDTO.get(i).setSuccessRate(percentage);
        }
        return playersDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerEntity> getOnePlayer(int id) {
        return Optional.ofNullable(playerRepository.findById(id));
    }

    @Override
    @Transactional
    public PlayerEntity savePlayer(PlayerEntity playerEntity) {
        if (playerEntity.getUserName().equals("ANONYMOUS") || !playerRepository.existsByUserName(playerEntity.getUserName())) {
            playerRepository.save(playerEntity);
        } else {
            playerEntity = null;
        }
        return playerEntity;
    }


    @Override
    @Transactional
    public void deleteDices(int id) {
        PlayerEntity playerEntity = playerRepository.findById(id);

        if (playerEntity != null) {
            playerEntity.getDicerolls().clear();
            playerRepository.save(playerEntity);
        }
    }

    @Override
    @Transactional
    public DiceEntity rollDice(int id) {
        PlayerEntity playerEntity = playerRepository.findById(id);
        DiceEntity diceEntity = new DiceEntity();

        if (playerEntity != null) {
            playerEntity.getDicerolls().add(diceEntity);
            playerRepository.save(playerEntity);
        }
        return diceEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiceEntity> getOneDiceRoll(int id) {
        PlayerEntity playerRepositoryById = playerRepository.findById(id);
        List<DiceEntity> dices = new ArrayList<>();
        List<DiceEntity> dicerolls = playerRepositoryById.getDicerolls();

        for (DiceEntity diceEntity : dicerolls) {
            dices.add(diceEntity);
        }
        return dices;
    }

    @Override
    public String getRanking() {
        int winners = 0;
        int j = 0;
        float percentage = 0.0F;
        List<PlayerEntity> dices = playerRepository.findAll();
        DiceEntity insidePlayer;
        int players = 0;

        for (int k = 0; k < dices.size(); k++) {
            for (int p = 0; p < dices.get(k).getDicerolls().size(); p++) {
                insidePlayer = dices.get(k).getDicerolls().get(p);
                players++;
                if (insidePlayer.getWinner_looser().equals("Winner")) {
                    winners++;
                }
            }
        }

        percentage = (winners * 100) / players;
        return "The percentage of winners is " + percentage + " %";
    }

    @Override
    public PlayerDTO getLoser() {
        PlayerDTO playerLoser = null;
        List<PlayerDTO> playersDTO = getAllPlayers();

        Collections.reverse(playersDTO);
        return playersDTO.get(1);
    }

    @Override
    public PlayerDTO getWinner() {
        PlayerDTO playerWinner = null;
        List<PlayerDTO> playersDTO = getAllPlayers();

        Collections.sort(playersDTO);
        return playersDTO.get(1);
    }

}

