package com.eventchase.consumer.confiugration;

import com.eventchase.consumer.RequestDTO;
import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
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
	private ExchangeConfig fanoutExchangeConfig;

	@Data
	private static class ExchangeConfig {
		private String exchangeName;
		private String queueName;
		private String routingKey;
	}

	@Bean
	MessageConverter getJsonMessageConverter() {
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
		jackson2JsonMessageConverter.setClassMapper(getClassMapper());
		return jackson2JsonMessageConverter;
	}

	@Bean
	public DefaultClassMapper getClassMapper()
	{
		DefaultClassMapper classMapper = new DefaultClassMapper();
		Map<String, Class<?>> idClassMapping = new HashMap<>();
		idClassMapping.put("com.eventchase.producer.exchange.RequestDTO", RequestDTO.class);
		classMapper.setIdClassMapping(idClassMapping);
		return classMapper;
	}

	@Bean RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(getJsonMessageConverter());
		return rabbitTemplate;
	}

	@Configuration
	public class TopicExchangeConfiguration {
		@Bean
		TopicExchange getTopicExchange() {
			return new TopicExchange(getTopicExchangeConfig().getExchangeName());
		}

		@Bean
		Queue getTopicQueue() {
			return new Queue(getTopicExchangeConfig().getQueueName());
		}

		@Bean
		Binding getTopicQueueBinding() {
			return BindingBuilder.bind(getTopicQueue())
					.to(getTopicExchange())
					.with(getTopicExchangeConfig().getRoutingKey());
		}
	}

	@Configuration
	public class DirectExchangeConfig {
		@Bean
		public DirectExchange getDirectExchange() {
			return new DirectExchange(getDirectExchangeConfig().getExchangeName());
		}

		@Bean
		Queue getDirectQueue() {
			return new Queue(getDirectExchangeConfig().getQueueName());
		}

		@Bean
		Binding getDirectQueueBinding() {
			return BindingBuilder.bind(getDirectQueue())
					.to(getDirectExchange())
					.with(getDirectExchangeConfig().getRoutingKey());
		}
	}

	@Configuration
	public class FanoutExchangeConfig {
		@Bean
		public FanoutExchange getFanoutExchange() {
			return new FanoutExchange(getFanoutExchangeConfig().getExchangeName());
		}

		@Bean
		Queue getFanoutQueue() {
			return new Queue(getFanoutExchangeConfig().getQueueName());
		}

		@Bean
		Binding getFanoutQueueBinding() {
			return BindingBuilder.bind(getFanoutQueue())
					.to(getFanoutExchange());
		}
	}
}
