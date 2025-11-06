package com.inn.kafkaConfiguration;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.inn.dto.EventMessageDTO;
import com.inn.roomConstants.RoomConstants;

@Service
public class RoomBillzProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomBillzProducer.class);
	
	@Value("${kafka.topics.roomBillz}")
	private String roomBillzKafkaTopic;

	private final KafkaTemplate<String, EventMessageDTO> kafkaTemplate;

	public RoomBillzProducer(KafkaTemplate<String, EventMessageDTO> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public Boolean eventPublisher(EventMessageDTO message) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "eventPublisher");
		try {
			CompletableFuture<SendResult<String, EventMessageDTO>> send = kafkaTemplate.send(roomBillzKafkaTopic, message);
			SendResult<String, EventMessageDTO> result = send.get();
			logger.info("✅ Event Published to RoomBillz Notification service: {}", kv("Message", message.getMessage()));
			logger.info("Topic: {}", kv("Topic", result.getRecordMetadata().topic()));
			logger.info("Offset: {}", kv("offset", result.getRecordMetadata().offset()));
			return true;
		} catch (Exception ex) {
			logger.error("❌ Failed to publish event: {}", ex.getMessage(), ex);
			return false;
		}
	}
}
