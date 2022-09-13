package dev.yonyon

import io.micronaut.openapi.annotation.OpenAPIInclude
import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.tags.Tag

@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "jwt"
)
@OpenAPIDefinition(
    info = Info(
        title = "core-system",
        version = "1.0-snapshot"
    )
)
@OpenAPIInclude(
    classes = [io.micronaut.security.endpoints.LoginController::class],
    tags = [Tag(name = "Security", description = "認証")],
    uri = "/api/login"
)
object Api {
}

fun main(args: Array<String>) {
    run(*args)
}

