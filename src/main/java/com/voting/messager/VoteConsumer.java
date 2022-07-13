package com.voting.messager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.voting.constant.ApiConstants.KAFKA_VOTE_GROUP;
import static com.voting.constant.ApiConstants.KAFKA_VOTE_TOPIC;

@Slf4j
@Service
public class VoteConsumer {
    @KafkaListener(topics = KAFKA_VOTE_TOPIC, groupId = KAFKA_VOTE_GROUP)
    public void listenGroupVote(String message) {
        log.info("Received Message in group voting: " + message);
    }

}
