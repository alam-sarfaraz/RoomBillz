package com.inn.kafkaConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.inn.dto.EventMessage;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${kafka.groups.roomBillz}")
	private String groupId;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		return props;
	}

	@Bean
	public ConsumerFactory<String, EventMessage> consumerFactory() {
		JsonDeserializer<EventMessage> deserializer = new JsonDeserializer<>(EventMessage.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		deserializer.setRemoveTypeHeaders(false);

		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), deserializer);
	}

	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, EventMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, EventMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		// Manual acknowledgment
		factory.getContainerProperties()
				.setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);

		// Always seek to latest message
		factory.getContainerProperties().setConsumerRebalanceListener(new ConsumerAwareRebalanceListener() {
			@Override
			public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
				if (partitions != null && !partitions.isEmpty()) {
					consumer.seekToEnd(partitions);
					System.out.println("🔁 Seeking to latest offset for partitions: " + partitions);
				}
			}
		});

		return factory;
	}
}
