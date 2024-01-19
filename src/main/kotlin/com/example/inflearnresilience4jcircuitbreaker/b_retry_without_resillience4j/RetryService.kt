package com.example.inflearnresilience4jcircuitbreaker.b_retry_without_resillience4j

import com.example.inflearnresilience4jcircuitbreaker.exception.RetryException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class RetryService {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Throws(InterruptedException::class)
    fun process(param: String): String {
        lateinit var result: String

        var retryCount = 0

        while (retryCount++ < MAX_ATTEMPS) {
            try {
                result = callAnotherServer(param)
            } catch (ex: RetryException) {
                if (retryCount == MAX_ATTEMPS) {
                    return fallback(param, ex)
                }

                Thread.sleep(WAIT_DURATION.toLong())
            }

            if (result != null) break
        }

        return result
    }

    private fun fallback(param: String, ex: Exception): String {
        // retry에 전부 실패해야 fallback이 실행
        log.info("### fallback! your request is $param")
        return "Recovered: $ex"
    }

    private fun callAnotherServer(param: String): String {
        // retry exception은 retry된다.
        throw RetryException("retry exception")

        // ignore exception은 retry하지 않고 바로 예외가 클라이언트에게 전달된다.
        //        throw new IgnoreException("ignore exception");
    }

    companion object {
        private const val MAX_ATTEMPS = 3
        private const val WAIT_DURATION = 1000
    }
}
