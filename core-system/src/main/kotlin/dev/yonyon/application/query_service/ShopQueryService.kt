package dev.yonyon.application.query_service

import dev.yonyon.enums.PrefectureEnum
import dev.yonyon.enums.SeatTypeEnum
import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.UnexpectedException
import dev.yonyon.infrastructure.table.SeatTable
import dev.yonyon.infrastructure.table.ShopTable
import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * 店舗クエリサービス
 */
@Singleton
class ShopQueryService(private val database: Database) {

    /**
     * 店舗リストを取得
     *
     * @return 店舗リスト
     */
    fun getShopsWithSheets(): List<ShopWithSheetsResult> {
        val shopWithSheetsResults = transaction {
            addLogger(StdOutSqlLogger)
            ShopTable.leftJoin(SeatTable) //
                .selectAll()//
                .groupBy(
                    {
                        ShopResultDto(
                            id = it[ShopTable.id].value,
                            name = it[ShopTable.name],
                            zipcode = it[ShopTable.zipcode],
                            prefecture = it[ShopTable.prefecture],
                            locality = it[ShopTable.locality],
                            street = it[ShopTable.street],
                            building = it[ShopTable.building],
                            hasParking = it[ShopTable.hasParking],
                            nonSmoking = it[ShopTable.nonSmoking],
                            imageUrl = it[ShopTable.imageUrl],
                            registeredAt = it[ShopTable.registeredAt].atOffset(ZoneOffset.UTC)
                        )
                    },
                    {
                        SeatResultDto(
                            id = it[SeatTable.id].value,
                            type = SeatTypeEnum.findById(it[SeatTable.type])?.type
                                ?: throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR),
                            capacity = it[SeatTable.capacity],
                            hasOutlet = it[SeatTable.hasOutlet],
                            isNearAirConditioner = it[SeatTable.isNearAirConditioner],
                            isUsed = it[SeatTable.isUsed],
                            registeredAt = it[SeatTable.registeredAt].atOffset(ZoneOffset.UTC)
                        )
                    },
                ).map { (shopResultDto, seats) ->
                    ShopWithSheetsResult(shopResultDto, seats)
                }
        }

        return shopWithSheetsResults
    }

}

@Introspected
data class ShopWithSheetsResult(
    val id: UUID,
    val name: String,
    val zipcode: String,
    val prefecture: String,
    val locality: String,
    val street: String,
    val building: String?,
    val hasParking: Boolean,
    val nonSmoking: Boolean,
    val imageUrl: String,
    val seats: List<SeatResultDto>,
    val registeredAt: OffsetDateTime
) {
    constructor(shopResultDto: ShopResultDto, seats: List<SeatResultDto>) : this(
        id = shopResultDto.id,
        name = shopResultDto.name,
        zipcode = shopResultDto.zipcode,
        prefecture = PrefectureEnum.findById(shopResultDto.prefecture)?.prefectureName
            ?: throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR),
        locality = shopResultDto.locality,
        street = shopResultDto.street,
        building = shopResultDto.building,
        hasParking = shopResultDto.hasParking,
        nonSmoking = shopResultDto.nonSmoking,
        imageUrl = shopResultDto.imageUrl,
        seats = seats,
        registeredAt = shopResultDto.registeredAt
    )
}

@Introspected
data class ShopResultDto(
    val id: UUID,
    val name: String,
    val zipcode: String,
    val prefecture: Int,
    val locality: String,
    val street: String,
    val building: String?,
    val hasParking: Boolean,
    val nonSmoking: Boolean,
    val imageUrl: String,
    val registeredAt: OffsetDateTime
)

@Introspected
data class SeatResultDto(
    val id: UUID,
    val type: String,
    val capacity: Int,
    val hasOutlet: Boolean,
    val isNearAirConditioner: Boolean,
    val isUsed: Boolean,
    val registeredAt: OffsetDateTime
)
