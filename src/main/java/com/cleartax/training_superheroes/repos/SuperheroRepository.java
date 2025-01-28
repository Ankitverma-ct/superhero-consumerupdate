package com.cleartax.training_superheroes.repos;

import com.cleartax.training_superheroes.dto.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SuperheroRepository extends MongoRepository<Superhero, String> {
//    void deleteByName(String name);
//
//    Optional<Superhero> findByName(String name);
    //Optional<Superhero> findByName(String name);

}
