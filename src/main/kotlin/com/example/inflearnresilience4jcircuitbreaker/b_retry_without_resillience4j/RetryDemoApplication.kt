package com.example.inflearnresilience4jcircuitbreaker.b_retry_without_resillience4j

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RetryDemoApplication {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(RetryDemoApplication::class.java, *args)
        }
    }
}
