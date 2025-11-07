package com.inn.kafkaConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
	    //Instant check: Is Kafka available?
	    try {
	        kafkaTemplate.partitionsFor(roomBillzKafkaTopic); 
	        // If Kafka is DOWN → This throws instantly → No wait
	    } catch (Exception ex) {
	        logger.error("❌ Kafka is DOWN: {}", ex.getMessage());
	        return false;
	    }
	    //Kafka is UP → Send async
	    try {
	        kafkaTemplate.send(roomBillzKafkaTopic, message).whenComplete((result, ex) -> {
	            if (ex == null) {
	                logger.info("✅ Event Published: {}", message.getMessage());
	            } else {
	                logger.error("❌ Send Failed Even Though Kafka Was Up: {}", ex.getMessage(), ex);  
	            }
	        });
	        return true;
	    } catch (Exception ex) {
	        logger.error("❌ Unexpected error while sending: {}", ex.getMessage());
	        return false;
	    }
	}


}
