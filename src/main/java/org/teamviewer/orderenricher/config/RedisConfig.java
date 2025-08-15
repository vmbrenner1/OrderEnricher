package org.teamviewer.orderenricher.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.teamviewer.orderenricher.model.OrderDetail;

/**
 * Configuration for the Redis template that is used to cache and store orderDetails sent to the application.
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, OrderDetail> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, OrderDetail> template = new RedisTemplate<>();

        // Functionality for timestamp serialization
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Jackson2JsonRedisSerializer<OrderDetail> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(OrderDetail.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        template.setConnectionFactory(lettuceConnectionFactory);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
