package com.voting.messager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.modal.tables.Vote;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteProducer {

    @Resource
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topicName;

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug(String.format("Sent message=[%s] with offset=[%s]",
                        message, result.getRecordMetadata().offset()));
            }

            @Override
            public void onFailure(@NonNull Throwable ex) {
                log.error(String.format("Unable to send message=[%s] due to : %s", message, ex.getMessage()));
            }
        });
    }

    public void sendMessage(Vote vote) {
        try {
            this.sendMessage(new ObjectMapper().writeValueAsString(vote));
        } catch (JsonProcessingException ignored) {}

    }
}
