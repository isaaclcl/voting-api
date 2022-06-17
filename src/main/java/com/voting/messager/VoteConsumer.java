package com.voting.messager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import static com.voting.constant.ApiConstants.KAFKA_VOTE_GROUP;
import static com.voting.constant.ApiConstants.KAFKA_VOTE_TOPIC;

@Slf4j
public class VoteConsumer {


    @KafkaListener(topics = KAFKA_VOTE_TOPIC, groupId = KAFKA_VOTE_GROUP)
    public void listenGroupFoo(String message) {
        log.info("Received Message in group foo: " + message);
    }

}
