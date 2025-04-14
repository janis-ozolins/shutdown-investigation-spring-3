package com.shut.demo;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
class EventBindingsLifecycle implements SmartLifecycle {
    private static final Logger log = LoggerFactory.getLogger(EventBindingsLifecycle.class);
    private boolean running = false;

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        sleepAllowEventsToFinish();
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MIN_VALUE + 1001;
    }

    private void sleepAllowEventsToFinish() {
        log.info("Received shutdown signal.");
        try {
            Duration sleepTime = Duration.ofSeconds(15);
            log.info("Sleeping for {} seconds after inbound binders should be shutdown.", sleepTime);
            Thread.sleep(sleepTime.toMillis());
            log.info("Continuing application shutdown");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep interrupted", e);
        }
    }
}
