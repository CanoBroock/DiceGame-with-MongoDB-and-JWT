package com.juegodados.CanoBroockCesar.model.repository;

import com.juegodados.CanoBroockCesar.model.domain.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
//Se pone anotación para que detecte que es un repositorio y así poder hacer de una forma mas facil
// las inyecciones de dependencia.
@Repository
//Heredamos lo métodos de Mongorepository para todas las operaciones CRUD. Se indica en primer lugar el tipo de objeto
//y después, el tipo de cual es nuestro ID
public interface PlayerRepository extends MongoRepository<PlayerEntity, Integer> {

    PlayerEntity findById(int userID);

    Boolean existsByUserName(String userName);

    @Query(value = "{userName:'?0'}")
    PlayerEntity findUserByUsername(String username);

}
