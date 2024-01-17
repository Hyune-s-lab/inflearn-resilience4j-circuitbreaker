package com.example.inflearnresilience4jcircuitbreaker.a_retry

import com.example.inflearnresilience4jcircuitbreaker.exception.RetryException
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RetryService {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Retry(name = SIMPLE_RETRY_CONFIG, fallbackMethod = "fallback")
    fun process(param: String): String {
        return callAnotherServer(param)
    }

    /**
     * retry 에 전부 실패해야 fallback 이 실행
     */
    private fun fallback(param: String, ex: Exception): String {
        log.info("### fallback! your request is $param")
        return "Recovered: $ex"
    }

    private fun callAnotherServer(param: String): String {
        // retry exception 은 retry 된다.
        log.info("### callAnotherServer! your request is $param")
        throw RetryException("retry exception")

        // ignore exception 은 retry 하지 않고 바로 예외가 클라이언트에게 전달된다.
//        log.info("### callAnotherServer! your request is $param")
//        throw IgnoreException("ignore exception");
    }

    companion object {
        private const val SIMPLE_RETRY_CONFIG = "simpleRetryConfig"
    }
}
