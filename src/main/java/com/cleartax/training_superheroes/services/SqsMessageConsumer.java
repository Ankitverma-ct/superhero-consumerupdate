package com.cleartax.training_superheroes.services;

import com.cleartax.training_superheroes.config.SqsConfig;
import com.cleartax.training_superheroes.dto.Superhero;
import com.cleartax.training_superheroes.dto.SuperheroRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;
import java.util.Optional;

@Service
public class SqsMessageConsumer {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private SqsConfig sqsConfig;

    @Autowired
    private SuperheroService superheroService;

    // Poll the queue every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void consumeSuperhero() {

        System.out.println("running");

        ReceiveMessageResponse receivedMessage = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .build());

        List<Message> li = receivedMessage.messages();

        for(Message m: li){
            String id = m.body();


            Superhero superhero = superheroService.getSuperhero(id)
                    .orElseThrow(() -> new RuntimeException("Superhero not found"));;
            superhero.setName("helloman");
            superheroService.updateSuperhero(id, superhero);

//      DeleteMessageResponse deletedMessage = sqsClient.deleteMessage(DeleteMessageRequest.builder()
//              .queueUrl(sqsConfig.getQueueUrl())
//              .receiptHandle(receivedMessage.messages().get(0).receiptHandle())
//              .build());

            DeleteMessageResponse deletedMessage = sqsClient.deleteMessage(DeleteMessageRequest.builder()
                    .queueUrl(sqsConfig.getQueueUrl())
                    .receiptHandle(m.receiptHandle())
                    .build());

            System.out.println("deleted message response "+ deletedMessage.toString());
        }}
}
