package com.eventchase.consumer.confiugration;

import com.eventchase.consumer.Order;
import lombok.Data;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("rabbitmq")
@Data
public class RabbitMQConsumerConfiguration {
	private String host;
	private String username;
	private String password;
	private int port;
	private ExchangeConfig topicExchangeConfig;
	private ExchangeConfig directExchangeConfig;
	private FanoutExchangeConfig fanoutExchangeConfig;

	@Data
	private static class ExchangeConfig {
		private String exchangeName;
		private String queueName;
		private String routingKey;
	}

	@Data
	private static class FanoutExchangeConfig {
		private String exchangeName;
		private String queue1;
		private String queue2;
		private String queue3;
	}

	@Bean
	MessageConverter getJsonMessageConverter() {
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		jackson2JsonMessageConverter.setClassMapper(getClassMapper());
		return jackson2JsonMessageConverter;
	}

	@Bean
	public DefaultClassMapper getClassMapper() {
		DefaultClassMapper classMapper = new DefaultClassMapper();
		Map<String, Class<?>> idClassMapping = new HashMap<>();
		idClassMapping.put("com.eventchase.producer.exchange.Order", Order.class);
		classMapper.setIdClassMapping(idClassMapping);
		return classMapper;
	}

	@Bean RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(getJsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	Declarables bindTopicQueue() {
		TopicExchange topicExchange = new TopicExchange(getTopicExchangeConfig().getExchangeName());
		Queue queue = new Queue(getTopicExchangeConfig().getQueueName());
		return new Declarables(topicExchange, queue,
				BindingBuilder.bind(queue)
						.to(topicExchange)
						.with(getTopicExchangeConfig().getRoutingKey())
		);
	}

	@Bean
	Declarables bindDirectQueue() {
		DirectExchange directExchange = new DirectExchange(getDirectExchangeConfig().getExchangeName());
		Queue queue = new Queue(getDirectExchangeConfig().getQueueName());
		return new Declarables(directExchange, queue, BindingBuilder.bind(queue)
				.to(directExchange)
				.with(getDirectExchangeConfig().getRoutingKey())
		);
	}

	@Bean
	Declarables bindFanoutQueues() {
		FanoutExchange fanoutExchange = new FanoutExchange(getFanoutExchangeConfig().getExchangeName());
		Queue queue1 = new Queue(getFanoutExchangeConfig().getQueue1());
		Queue queue2 = new Queue(getFanoutExchangeConfig().getQueue2());
		Queue queue3 = new Queue(getFanoutExchangeConfig().getQueue3());
		return new Declarables(fanoutExchange,
				queue1,
				queue2,
				queue3,
				BindingBuilder.bind(queue1)
						.to(fanoutExchange),
				BindingBuilder.bind(queue2)
						.to(fanoutExchange),
				BindingBuilder.bind(queue3)
						.to(fanoutExchange)
		);
	}
}
