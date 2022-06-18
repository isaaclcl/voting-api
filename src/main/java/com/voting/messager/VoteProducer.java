package com.voting.messager;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

import static com.voting.constant.ApiConstants.KAFKA_VOTE_TOPIC;

@Slf4j
public class VoteProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(KAFKA_VOTE_TOPIC, message);

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

}
