package com.example.inflearnresilience4jcircuitbreaker.d_circuitbreaker_event

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnIgnoredErrorEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSlowCallRateExceededEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent
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
                log.info("RegistryEventConsumer.onEntryAddedEvent")

                val eventPublisher = entryAddedEvent.addedEntry.eventPublisher

                eventPublisher.onEvent { event: CircuitBreakerEvent -> log.info("### onEvent $event") }
                eventPublisher.onSuccess { event: CircuitBreakerOnSuccessEvent -> log.info("onSuccess $event") }
                eventPublisher.onCallNotPermitted { event: CircuitBreakerOnCallNotPermittedEvent ->
                    log.info(
                        "### onCallNotPermitted $event"
                    )
                }
                eventPublisher.onError { event: CircuitBreakerOnErrorEvent -> log.info("onError $event") }
                eventPublisher.onIgnoredError { event: CircuitBreakerOnIgnoredErrorEvent ->
                    log.info(
                        "### onIgnoredError $event"
                    )
                }

                eventPublisher.onStateTransition { event: CircuitBreakerOnStateTransitionEvent ->
                    log.info(
                        "### onStateTransition $event"
                    )
                }

                eventPublisher.onSlowCallRateExceeded { event: CircuitBreakerOnSlowCallRateExceededEvent ->
                    log.info(
                        "### onSlowCallRateExceeded $event"
                    )
                }
                eventPublisher.onFailureRateExceeded { event: CircuitBreakerOnFailureRateExceededEvent ->
                    log.info(
                        "### onFailureRateExceeded $event"
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
