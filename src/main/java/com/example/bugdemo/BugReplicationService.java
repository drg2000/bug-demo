package com.example.bugdemo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BugReplicationService {

  @Cacheable(cacheNames = RedisConfig.A_NAME, cacheManager = "redisCacheManager")
  public Mono<String> callCacheableMethod() {
    return Mono.just("result") //
        .doOnSuccess(result -> throwMethod());
  }

  private void throwMethod() {
    throw new SpecificException();
  }

  class SpecificException extends RuntimeException {

  }

}
