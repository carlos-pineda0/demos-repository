package com.example.pubsub;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class PubsubApplication {
//	@Value("${spring.cloud.gcp.project-id}")
//	String projectId;

	public static void main(String[] args) {
		SpringApplication.run(PubsubApplication.class, args);
	}

//	@Bean
//	public Topic createTopic() {
//		String topicName = "testTopic";
//
//		try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
//			ProjectTopicName theTopicName = ProjectTopicName.of(projectId, topicName);
//			Topic topic = topicAdminClient.createTopic(theTopicName);
//			log.info("Topic created: " + topic.getName());
//			return topic;
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("pubsubInputChannel") MessageChannel inputChannel,
			PubSubTemplate pubSubTemplate
			) {

		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "testSubscription");
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);

		return adapter;
	}

	@Bean
	public MessageChannel pubsubInputChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "pubsubInputChannel")
	public MessageHandler messageReceiver() {
		return message -> {
			log.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
			BasicAcknowledgeablePubsubMessage originalMessage =
					message
							.getHeaders()
							.get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);

			originalMessage.ack();
		};
	}

	@Bean
	@ServiceActivator(inputChannel = "pubsubOutputChannel")
	public MessageHandler messageSender(PubSubTemplate pubSubTemplate) {
		return new PubSubMessageHandler(pubSubTemplate, "testTopic");
	}



}
