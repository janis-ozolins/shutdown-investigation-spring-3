package com.shut.demo.consumer;

import com.shut.demo.supplier.ShutdownTestSupplier;
import java.time.Duration;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component("consumeShutdownTest")
class ShutdownTestConsumer implements Consumer<ShutdownTestDto> {

    private static final Logger log = LoggerFactory.getLogger(ShutdownTestConsumer.class);

    @Autowired
    private ShutdownTestSupplier supplier;

    @Override
    public void accept(ShutdownTestDto event) {
        log.info("Consuming test event: {}, source: {}", event.id(), event.source());
        sleep(event);
        emitEventIfNeeded(event);
    }

    private void emitEventIfNeeded(ShutdownTestDto event) {
        if (event.source().equals("test")) {
            ShutdownTestDto newDto = new ShutdownTestDto(event.id() + "-out", "consumer");

            log.info("Triggering new event from consumer: {}", newDto.id());
            supplier.getSink().emitNext(
                MessageBuilder.withPayload(newDto).build(),
                Sinks.EmitFailureHandler.busyLooping(Duration.ofMillis(500))
            );
        }
    }

    private void sleep(ShutdownTestDto event) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while processing event {}", event.id());
        }
    }
}
