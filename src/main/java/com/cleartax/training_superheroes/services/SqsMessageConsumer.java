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

    private final SqsClient sqsClient;
    private final SqsConfig sqsConfig;
    private final SuperheroService superheroService;

    @Autowired
    public SqsMessageConsumer(SqsClient sqsClient, SqsConfig sqsConfig, SuperheroService superheroService) {
        this.sqsClient = sqsClient;
        this.sqsConfig = sqsConfig;
        this.superheroService = superheroService;
    }

    // Scheduled task to poll messages from the queue every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void processQueueMessages() {
        System.out.println("Checking for messages in the queue...");

        // Fetch messages from the SQS queue
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .build();
        ReceiveMessageResponse response = sqsClient.receiveMessage(request);

        List<Message> messages = response.messages();

        // If no messages are present
        if (messages.isEmpty()) {
            System.out.println("No new messages to process.");
            return;
        }

        // Process each message
        for (Message message : messages) {
            String superheroId = message.body();

            try {
                // Retrieve and update the superhero entity
                Superhero superhero = superheroService.getSuperhero(superheroId)
                        .orElseThrow(() -> new IllegalArgumentException("Superhero not found for ID: " + superheroId));

                superhero.setName("Helloman");
                superheroService.updateSuperhero(superheroId, superhero);

                // Delete the processed message from the queue
                deleteMessageFromQueue(message.receiptHandle());
                System.out.println("Successfully processed and deleted message with ID: " + superheroId);
            } catch (Exception e) {
                System.err.println("Error processing message with ID: " + superheroId + ". Error: " + e.getMessage());
            }
        }
    }

    private void deleteMessageFromQueue(String receiptHandle) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .receiptHandle(receiptHandle)
                .build();

        DeleteMessageResponse deleteResponse = sqsClient.deleteMessage(deleteRequest);
        System.out.println("Message deleted from queue. Response: " + deleteResponse);
    }
}
