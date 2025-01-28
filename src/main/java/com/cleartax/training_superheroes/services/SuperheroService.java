package com.cleartax.training_superheroes.services;

import com.cleartax.training_superheroes.dto.Superhero;
import com.cleartax.training_superheroes.dto.SuperheroRequestBody;
import com.cleartax.training_superheroes.repos.SuperheroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperheroService {

    private SuperheroRepository superheroRepository;

    public SuperheroService(SuperheroRepository superheroRepository){
        this.superheroRepository = superheroRepository;
    }

    public Optional<Superhero> getSuperhero(String id){
        return superheroRepository.findById(id);
    }

//    private Superhero getByName(String name){
//        return
//    }

//    private Superhero  getByUniverse(String universe){
//        Superhero superhero =  new Superhero();
//        superhero.setUniverse(universe);
//        return superhero;
//    }

    private Superhero getDummyDate(String name){
        Superhero superhero =  new Superhero();
        superhero.setName(name);
        return superhero;
    }

    public Superhero persistSuperhero(SuperheroRequestBody requestBody){
        Superhero superhero = new Superhero();
        superhero.setName(requestBody.getSuperheroName());
        superhero.setPower(requestBody.getPower());
        superhero.setUniverse(requestBody.getUniverse());

        return superheroRepository.save(superhero);
    }

    public Superhero updateSuperhero(String id, Superhero newEntry) {
        Superhero existingSuperhero = superheroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Superhero not found with name: " + id));

        existingSuperhero.setName(newEntry.getName());
        existingSuperhero.setPower(newEntry.getPower());
        existingSuperhero.setUniverse(newEntry.getUniverse());

        return superheroRepository.save(existingSuperhero);
    }
//
//
//
//    public void deleteSuperhero(String heroName) {
//        Superhero existingSuperhero = superheroRepository.findByName(heroName)
//                .orElseThrow(() -> new RuntimeException("Superhero not found with name: " + heroName));
//
//        superheroRepository.delete(existingSuperhero);
//    }


    public List<Superhero> getAllSuperhero() {
        return superheroRepository.findAll();
    }

    public void deleteSuperhero(String id) {
        Superhero existinghero= superheroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Superhero not found with name: " + id));

        superheroRepository.delete(existinghero);
    }
}
