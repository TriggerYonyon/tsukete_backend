package dev.yonyon.api.controller

import dev.yonyon.api.request.SheetPurgeRequest
import dev.yonyon.api.response.SheetUsedEventResponse
import dev.yonyon.application.service.SheetUsedEventService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.*

/**
 * 座席コントローラー
 */
@Tag(name = "Sheet")
@Controller("/api/sheets")
@Secured(SecurityRule.IS_ANONYMOUS)
class SheetController(private val sheetUsedEventService: SheetUsedEventService) {

    /**
     * 座席確保API
     *
     * @param sheetId  座席ID
     * @return 座席使用イベントレスポンス
     */
    @Post("/{sheet_id}/reserve")
    fun reserve(
        @PathVariable("sheet_id") sheetId: UUID
    ): SheetUsedEventResponse {
        val trackingId = sheetUsedEventService.reserveSheet(sheetId)
        return SheetUsedEventResponse(trackingId)
    }

    /**
     * 座席解放API
     *
     * @param sheetId 座席ID
     */
    @Post("/{sheet_id}/purge")
    fun purge(
        @PathVariable("sheet_id") sheetId: UUID,
        @Body requestBody: SheetPurgeRequest
    ) {
        sheetUsedEventService.purgeSheet(sheetId, requestBody.trackingId)
    }
}
