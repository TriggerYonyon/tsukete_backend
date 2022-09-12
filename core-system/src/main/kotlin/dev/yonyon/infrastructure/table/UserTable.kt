package dev.yonyon.infrastructure.table

import io.micronaut.core.annotation.Introspected
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

@Introspected
object UserTable : UUIDTable("users") {
    val firstName = varchar("first_name", 255)

    val lastName = varchar("last_name", 255)

    val nickName = varchar("nick_name", 255)

    val email = varchar("email", 255)

    val password = varchar("password", 255)

    val registeredAt = datetime("registered_at")
}
