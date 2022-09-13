package dev.yonyon.api.controller

import dev.yonyon.application.query_service.UserInfoResult
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserRestControllerTest : BaseRestControllerTest() {
    // API PATH
    private val basePath = "/api/users"
    private val getUserInfoPath = "$basePath/me"

    @Test
    fun successToGetUserInfo() {
        val accessToken = this.login()
        val request = HttpRequest.GET<String>(getUserInfoPath).bearerAuth(accessToken)
        val response = client.toBlocking().exchange(request, UserInfoResult::class.java)
        Assertions.assertEquals(HttpStatus.OK, response.status)

        val body = response.body.get()
        Assertions.assertEquals(user.id, body.id)
        Assertions.assertEquals(user.email, body.email)
    }

    @Test
    fun failedWithUnauthorizedErrorWhenNotLogin() {
        val request = HttpRequest.GET<String>(getUserInfoPath)
        try {
            client.toBlocking().exchange(request, UserInfoResult::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.status)
        }
    }

}
