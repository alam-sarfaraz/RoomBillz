package com.inn.kafkaConfiguration;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessage;
import com.inn.roomConstants.RoomConstants;

@Service
public class RoomBillzConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomBillzConsumer.class);

	@KafkaListener(topics = "${kafka.topics.notification}",groupId = "${kafka.groups.roomBillz}")
    public void consumeNotificationEvent(EventMessage message) {
    	logger.info(RoomConstants.INSIDE_THE_METHOD + "Event received from Notification Service:{}", kv("Message",message.getMessage()));
    	// Process event and send notification
    }
}