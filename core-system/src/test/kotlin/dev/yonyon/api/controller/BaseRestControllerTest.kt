package dev.yonyon.api.controller

import dev.yonyon.domain.model.UserModel
import dev.yonyon.util.AuthUtil
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.sql.DriverManager
import java.util.*

@MicronautTest
open class BaseRestControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var authUtil: AuthUtil

    lateinit var user: UserModel

    val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:55432/yonyon",
        "yonyon",
        "yonyon"
    )

    @BeforeEach
    fun setup() {
        user = UserModel(
            id = UUID.randomUUID(),
            firstName = "test",
            lastName = "test",
            nickName = "test",
            email = "test@yonyon.dev",
            authUtil.encode("test"),
        )
        connection.prepareStatement(
            """
                INSERT INTO users(id, first_name, last_name, nick_name, email, password)
                    VALUES ('${user.id}', '${user.firstName}', '${user.lastName}', '${user.nickName}', '${user.email}', '${user.password}')
            """.trimIndent()
        ).execute()
    }

    @AfterEach
    fun cleanup() {
        connection.prepareStatement("TRUNCATE users CASCADE ").execute()
        connection.prepareStatement("TRUNCATE shops CASCADE ").execute()
        connection.prepareStatement("TRUNCATE seats CASCADE ").execute()
    }

    fun login(): String {
        val credentials = UsernamePasswordCredentials(user.email, "test")
        val request = HttpRequest.POST("/api/login", credentials)
        val response = client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        val body = response.body.get()
        return body.accessToken
    }

}
