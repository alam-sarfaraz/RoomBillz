package com.inn.kafkaConfiguration;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessage;
import com.inn.roomConstants.RoomConstants;

@Service
public class RoomBillzProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomBillzProducer.class);
	
	@Value("${kafka.topics.roomBillz}")
	private String roomBillzKafkaTopic;

	private final KafkaTemplate<String, EventMessage> kafkaTemplate;

	public RoomBillzProducer(KafkaTemplate<String, EventMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void eventPublisher(EventMessage message) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "eventPublisher");
		kafkaTemplate.send(roomBillzKafkaTopic, message);
		logger.info("Event Published to RoomBillz Notification service:{}", kv("Message",message.getMessage()));
	}
}
