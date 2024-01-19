package com.example.inflearnresilience4jcircuitbreaker.e_circuitbreaker_fallback

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitbreakerController(private val circuitbreakerService: CircuitbreakerService) {
    @GetMapping("/api-call")
    fun apiCall(@RequestParam param: String): String {
        return circuitbreakerService.process(param)
    }
}
