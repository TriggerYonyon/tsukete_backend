package dev.yonyon.infrastructure.table

import io.micronaut.core.annotation.Introspected
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

@Introspected
object ShopTable : UUIDTable(name = "shops") {
    val name = varchar("name", 255)

    val zipcode = varchar("zipcode", 7)

    val prefecture = integer("prefecture")

    val locality = varchar("locality", 255)

    val street = varchar("street", 255)

    val building = varchar("building", 255).nullable()

    val hasParking = bool("has_parking")

    val nonSmoking = bool("non_smoking")

    val imageUrl = varchar("image_url", 255)

    val registeredAt = datetime("registered_at")
}
