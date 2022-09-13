package dev.yonyon.infrastructure.repotiry_impl

import dev.yonyon.domain.model.UserModel
import dev.yonyon.domain.repository.UserRepository
import dev.yonyon.infrastructure.table.UserTable
import io.micronaut.context.annotation.Infrastructure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneOffset

/**
 * ユーザーリポジトリ
 */
@Infrastructure
class UserRepositoryImpl(private val database: Database) : UserRepository {

    /**
     * メールアドレスからユーザーを取得
     *
     * @param email メールアドレス
     * @return ユーザー
     */
    override fun findByEmail(email: String): UserModel? {
        val user = transaction {
            addLogger(StdOutSqlLogger)
            UserTable.select {
                UserTable.email eq email
            }.map {
                UserModel(
                    id = it[UserTable.id].value,
                    firstName = it[UserTable.firstName],
                    lastName = it[UserTable.lastName],
                    nickName = it[UserTable.nickName],
                    email = it[UserTable.email],
                    password = it[UserTable.password],
                    registeredAt = it[UserTable.registeredAt].atOffset(ZoneOffset.UTC)
                )
            }.firstOrNull()
        }

        return user
    }

}
