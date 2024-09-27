package com.example.bugdemo;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEvent {

  private final BugReplicationService bugReplicationService;

  public ApplicationReadyEvent(BugReplicationService bugReplicationService) {
    this.bugReplicationService = bugReplicationService;
  }

  @EventListener
  public void initialize(org.springframework.boot.context.event.ApplicationReadyEvent event) {
    try {
      bugReplicationService.callCacheableMethod() //
          .block();
    } catch (Exception e) {
      System.out.println(e.getClass().getName());
    }
  }

}
