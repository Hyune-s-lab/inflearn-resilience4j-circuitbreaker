package com.example.inflearnresilience4jcircuitbreaker.c_circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.core.registry.EntryAddedEvent
import io.github.resilience4j.core.registry.EntryRemovedEvent
import io.github.resilience4j.core.registry.EntryReplacedEvent
import io.github.resilience4j.core.registry.RegistryEventConsumer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CircuitbreakerDemoApplication {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun myRegistryEventConsumer(): RegistryEventConsumer<CircuitBreaker> {
        return object: RegistryEventConsumer<CircuitBreaker> {
            override fun onEntryAddedEvent(entryAddedEvent: EntryAddedEvent<CircuitBreaker>) {
                log.info("### RegistryEventConsumer.onEntryAddedEvent")
                entryAddedEvent.addedEntry.eventPublisher.onEvent { event -> log.info("### onEvent: $event") }
                entryAddedEvent.addedEntry.eventPublisher.onFailureRateExceeded { event ->
                    log.info(
                        "### onFailureRateExceeded: ${event.eventType}"
                    )
                }
            }

            override fun onEntryRemovedEvent(entryRemoveEvent: EntryRemovedEvent<CircuitBreaker>) {
                log.info("### RegistryEventConsumer.onEntryRemovedEvent")
            }

            override fun onEntryReplacedEvent(entryReplacedEvent: EntryReplacedEvent<CircuitBreaker>) {
                log.info("### RegistryEventConsumer.onEntryReplacedEvent")
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(CircuitbreakerDemoApplication::class.java, *args)
        }
    }
}
