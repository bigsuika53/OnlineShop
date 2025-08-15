package com.bigsuika.onlineshop.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 键序列化（String类型）
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 值序列化（JSON格式）
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash键序列化（String类型）
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // Hash值序列化（JSON格式）
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public CommandLineRunner testRedisConnection(RedisConnectionFactory connectionFactory) {
        return args -> {
            try {
                RedisConnection connection = connectionFactory.getConnection();
                System.out.println("✅ Redis 连接成功: " + connection.ping());
            } catch (Exception e) {
                System.err.println("❌ Redis 连接失败: " + e.getMessage());
            }
        };
    }
}
