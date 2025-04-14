package com.shut.demo.supplier;

import com.shut.demo.consumer.ShutdownTestDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service("produceShutdownTest")
public class ShutdownTestSupplier extends ServiceBusEventSupplier<ShutdownTestDto> {

    ShutdownTestSupplier() {
        super(Sinks.many().unicast().onBackpressureBuffer());
    }

}
