package com.shut.demo.supplier;

import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


public class ServiceBusEventSupplier<T> implements Supplier<Flux<Message<T>>> {

    private static final Logger log = LoggerFactory.getLogger(ServiceBusEventSupplier.class);

    private final Sinks.Many<Message<T>> sink;

    public ServiceBusEventSupplier(Sinks.Many<Message<T>> sink) {
        this.sink = sink;
    }

    @Override
    public Flux<Message<T>> get() {
        return sink.asFlux()
            .doOnError(t -> log.error("Error encountered while sending event", t));
    }

    public Sinks.Many<Message<T>> getSink() {
        return sink;
    }
}
