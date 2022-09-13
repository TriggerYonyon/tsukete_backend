package dev.yonyon.api.controller

import dev.yonyon.application.query_service.UserInfoResult
import dev.yonyon.application.query_service.UserQueryService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag

/**
 * ユーザーコントローラー
 */
@Tag(name = "User")
@SecurityRequirement(name = "BearerAuth")
@Controller("/api/users")
@Secured(SecurityRule.IS_AUTHENTICATED)
class UserRestController(private val userQueryService: UserQueryService) {

    /**
     * ユーザー情報取得API
     *
     * @param authentication 認証情報
     * @return ユーザー情報
     */
    @Get("/me")
    @Status(HttpStatus.OK)
    fun getUserInfo(
        @Parameter(hidden = true) authentication: Authentication
    ): UserInfoResult {
        return userQueryService.getUserInfo(authentication)
    }

}
