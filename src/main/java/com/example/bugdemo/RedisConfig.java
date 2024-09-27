package com.example.bugdemo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.util.Set;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisConfig {

  public static final String A_NAME = "aName";

  @Bean
  public LettuceConnectionFactory reactiveRedisConnectionFactory() {
    return new LettuceConnectionFactory("localhost", 6379);
  }

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    var genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer() //
        .configure(obj -> obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    return RedisCacheConfiguration.defaultCacheConfig() //
        .serializeValuesWith(SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));
  }

  @Bean
  public CacheManager redisCacheManager(LettuceConnectionFactory cacheManagerLettuceConnectionFactory,
      RedisCacheConfiguration redisCacheConfiguration) {
    return RedisCacheManager.builder(cacheManagerLettuceConnectionFactory) //
        .cacheDefaults(redisCacheConfiguration) //
        .initialCacheNames(Set.of( //
            A_NAME //
        )) //
        .enableStatistics() //
        .build();
  }

}
