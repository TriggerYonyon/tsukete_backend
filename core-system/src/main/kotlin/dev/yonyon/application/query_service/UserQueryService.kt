package dev.yonyon.application.query_service

import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.NotFoundException
import dev.yonyon.factory.DbClientFactory
import dev.yonyon.infrastructure.table.UserTable
import io.micronaut.core.annotation.Introspected
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * ユーザークエリサービス
 */
@Singleton
class UserQueryService(private val dbClientFactory: DbClientFactory) {

    /**
     * ユーザー情報を取得
     *
     * @param authentication 認証情報
     * @return ユーザー情報
     */
    fun getUserInfo(authentication: Authentication): UserInfoResult {
        val userId = UUID.fromString(authentication.name)
        dbClientFactory.getDbClient()
        val result = transaction {
            UserTable.select {
                UserTable.id eq userId
            }.map {
                UserInfoResult(
                    id = it[UserTable.id].value,
                    firstName = it[UserTable.firstName],
                    lastName = it[UserTable.lastName],
                    nickName = it[UserTable.nickName],
                    email = it[UserTable.email],
                    registeredAt = it[UserTable.registeredAt].atOffset(ZoneOffset.UTC)
                )
            }.firstOrNull() ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER)
        }

        return result
    }

}

@Introspected
data class UserInfoResult(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val nickName: String,
    val email: String,
    val registeredAt: OffsetDateTime
)
