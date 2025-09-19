package com.ecomm.common.config;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration
 * 
 * Configuration class for RabbitMQ message broker integration in the eMall platform.
 * This configuration provides:
 * - JSON message converter for serializing/deserializing messages
 * - Conditional configuration based on RabbitTemplate availability
 * - Support for asynchronous messaging between microservices
 * 
 * The configuration enables JSON-based message exchange between services,
 * facilitating decoupled communication and event-driven architecture patterns.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@Configuration
@ConditionalOnClass(RabbitTemplate.class)
public class RabbitMQConfig {
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
