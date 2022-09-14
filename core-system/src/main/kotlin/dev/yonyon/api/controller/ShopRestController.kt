package dev.yonyon.api.controller

import dev.yonyon.application.query_service.ShopQueryService
import dev.yonyon.application.query_service.ShopWithSheetsResult
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag

/**
 * 店舗コントローラー
 */
@Tag(name = "Shop")
@SecurityRequirement(name = "BearerAuth")
@Controller("/api/shops")
@Secured(SecurityRule.IS_ANONYMOUS)
class ShopRestController(private val shopQueryService: ShopQueryService) {

    /**
     * 店舗取得API
     *
     * @return 店舗リスト
     */
    @Get
    fun getShop(): List<ShopWithSheetsResult> {
        return shopQueryService.getShopsWithSheets()
    }

}
