package dev.yonyon.api.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Health Check")
@SecurityRequirement(name = "BearerAuth")
@Controller("/health")
@Secured(SecurityRule.IS_AUTHENTICATED)
class HealthController {

    @Get("/check")
    fun check(): String {
        return "OK"
    }

}
