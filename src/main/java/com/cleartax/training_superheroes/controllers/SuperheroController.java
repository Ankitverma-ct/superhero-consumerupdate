package com.cleartax.training_superheroes.controllers;


import com.cleartax.training_superheroes.config.SqsClientConfig;
import com.cleartax.training_superheroes.config.SqsConfig;
import com.cleartax.training_superheroes.dto.Superhero;
import com.cleartax.training_superheroes.dto.SuperheroRequestBody;
import com.cleartax.training_superheroes.services.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class SuperheroController {

    private SuperheroService superheroService;
    @Autowired
    private SqsConfig sqsConfig;

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private SqsClientConfig sqsClientConfig;

    @Autowired
    public SuperheroController(SuperheroService superheroService){
        this.superheroService = superheroService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", defaultValue = "World") String username) {
        return String.format("Hello %s!", username);
    }


    @GetMapping("/superhero/{id}")
    public Optional<Superhero> getSuperhero(@PathVariable String id){
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .messageBody(id)
                .build());
        return superheroService.getSuperhero(id);
    }

    @GetMapping("/getAllSuperhero")
    public List<Superhero> getAllSuperhero(){
        return superheroService.getAllSuperhero();
    }

    @PostMapping("/superhero")
    public Superhero persistSuperhero(@RequestBody SuperheroRequestBody superhero){
        return superheroService.persistSuperhero(superhero);
    }

//    @RequestMapping(value = "/superhero/{heroName}", method = RequestMethod.PUT)
//    public Superhero updateSuperhero(@PathVariable("heroName") String heroName, @RequestBody SuperheroRequestBody updateEntry) {
//        System.out.println("Received heroName: " + heroName);
//        return superheroService.updateSuperhero(heroName, updateEntry);
//    }

    @RequestMapping(value = "/superhero/{id}", method = RequestMethod.DELETE)
    public void deleteSuperhero(@PathVariable("id") String Id) {
        System.out.println("Received heroName: " + Id);
        superheroService.deleteSuperhero(Id);
    }



}
