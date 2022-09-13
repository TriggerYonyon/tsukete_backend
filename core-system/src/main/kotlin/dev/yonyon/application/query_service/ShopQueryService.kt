package dev.yonyon.application.query_service

import dev.yonyon.enums.PrefectureEnum
import dev.yonyon.enums.SheetTypeEnum
import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.UnexpectedException
import dev.yonyon.factory.DbClientFactory
import dev.yonyon.infrastructure.table.SheetTable
import dev.yonyon.infrastructure.table.ShopTable
import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton
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
class ShopQueryService(private val dbClientFactory: DbClientFactory) {

    /**
     * 店舗リストを取得
     *
     * @return 店舗リスト
     */
    fun getShopsWithSheets(): List<ShopWithSheetsResult> {
        dbClientFactory.getDbClient()
        val shopWithSheetsResults = transaction {
            addLogger(StdOutSqlLogger)
            ShopTable.leftJoin(SheetTable) //
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
                        SheetResultDto(
                            id = it[SheetTable.id].value,
                            type = SheetTypeEnum.findById(it[SheetTable.type])?.type
                                ?: throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR),
                            capacity = it[SheetTable.capacity],
                            hasOutlet = it[SheetTable.hasOutlet],
                            isNearAirConditioner = it[SheetTable.isNearAirConditioner],
                            isUsed = it[SheetTable.isUsed],
                            registeredAt = it[SheetTable.registeredAt].atOffset(ZoneOffset.UTC)
                        )
                    },
                ).map { (shopResultDto, sheets) ->
                    ShopWithSheetsResult(shopResultDto, sheets)
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
    val sheets: List<SheetResultDto>,
    val registeredAt: OffsetDateTime
) {
    constructor(shopResultDto: ShopResultDto, sheets: List<SheetResultDto>) : this(
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
        sheets = sheets,
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
data class SheetResultDto(
    val id: UUID,
    val type: String,
    val capacity: Int,
    val hasOutlet: Boolean,
    val isNearAirConditioner: Boolean,
    val isUsed: Boolean,
    val registeredAt: OffsetDateTime
)
