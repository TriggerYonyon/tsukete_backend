package dev.yonyon.infrastructure.table

import io.micronaut.core.annotation.Introspected
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

@Introspected
object SeatTable : UUIDTable("seats") {
    val shopId = reference("shop_id", ShopTable)

    val type = integer("type")

    val capacity = integer("capacity")

    val hasOutlet = bool("has_outlet")

    val isNearAirConditioner = bool("is_near_air_conditioner")

    val isUsed = bool("is_used")

    val registeredAt = datetime("registered_at")

}
