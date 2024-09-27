package com.example.bugdemo;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CaffeineConfig implements CachingConfigurer {


  @Bean
  @Primary
  public CacheManager caffeineCacheManager() {
    var cacheManager = new CaffeineCacheManager();
    cacheManager.setAsyncCacheMode(true);
    var defaultConfiguration = Caffeine.newBuilder() //
        .maximumSize(500) //
        .expireAfterWrite(5, TimeUnit.SECONDS);
    cacheManager.setCaffeine(defaultConfiguration);
    cacheManager.registerCustomCache("aCache", Caffeine.newBuilder() //
        .maximumSize(500) //
        .expireAfterWrite(5, TimeUnit.SECONDS) //
        .recordStats() //
        .buildAsync());

    return cacheManager;
  }

  @Bean
  @Primary
  @Override
  public KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator();
  }

  @Override
  public CacheErrorHandler errorHandler() {
    return new LoggingCacheErrorHandler(true);
  }

}
