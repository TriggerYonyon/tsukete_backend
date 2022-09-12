package dev.yonyon.api.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/health")
class HealthController {

    @Get("/check")
    fun check(): String {
        return "OK"
    }

}
