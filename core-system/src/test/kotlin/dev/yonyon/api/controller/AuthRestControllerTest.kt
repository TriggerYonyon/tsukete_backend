package dev.yonyon.api.controller

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import dev.yonyon.exception.ErrorCode
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AuthRestControllerTest : BaseRestControllerTest() {


    @Test
    fun successToLogin() {
        val credentials = UsernamePasswordCredentials(user.email, "test")
        val request = HttpRequest.POST("/api/login", credentials)
        val response: HttpResponse<BearerAccessRefreshToken> = client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        val body = response.body()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertEquals(user.id.toString(), body?.username)
        Assertions.assertNotNull(body?.accessToken)
        Assertions.assertTrue(JWTParser.parse(body?.accessToken) is SignedJWT)
    }

    @Test
    fun failedWithNotFoundErrorWhenUserIsNotExist() {
        val credentials = UsernamePasswordCredentials("wrongEmail", "test")
        val request = HttpRequest.POST("/api/login", credentials)
        try {
            client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.status)
            Assertions.assertEquals(ErrorCode.NOT_FOUND_USER.message, exception.message)
        }
    }

    @Test
    fun failedWithUnauthorizedErrorWhenPasswordIsWrong() {
        val credentials = UsernamePasswordCredentials(user.email, "wrongPassword")
        val request = HttpRequest.POST("/api/login", credentials)
        try {
            client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        } catch (exception: HttpClientResponseException) {
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.status)
            Assertions.assertEquals(ErrorCode.INVALID_CERTIFICATIONS.message, exception.message)
        }
    }

}
