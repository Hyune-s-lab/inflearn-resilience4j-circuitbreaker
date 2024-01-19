package com.example.inflearnresilience4jcircuitbreaker.c_circuitbreaker

import com.example.inflearnresilience4jcircuitbreaker.exception.IgnoreException
import com.example.inflearnresilience4jcircuitbreaker.exception.RecordException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class CircuitbreakerService {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @CircuitBreaker(name = SIMPLE_CIRCUIT_BREAKER_CONFIG, fallbackMethod = "fallback")
    @Throws(InterruptedException::class)
    fun process(param: String): String {
        return callAnotherServer(param)
    }

    private fun fallback(param: String, ex: Exception): String {
        // fallback 은 ignoreException 이 발생해도 실행된다.
        log.info("### fallback! your request is $param")
        return "Recovered: $ex"
    }

    @Throws(InterruptedException::class)
    private fun callAnotherServer(param: String): String {
        when (param) {
            "a" -> throw RecordException("record exception")
            "b" -> throw IgnoreException("ignore exception")
            "c" -> Thread.sleep(4000) // 3초 이상 걸리는 경우도 실패로 간주하기 위한 분기
        }

        return param
    }

    companion object {
        private const val SIMPLE_CIRCUIT_BREAKER_CONFIG = "simpleCircuitBreakerConfig"
    }
}
