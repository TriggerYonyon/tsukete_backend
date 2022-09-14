package dev.yonyon.api.controller

import dev.yonyon.api.request.SeatPurgeRequest
import dev.yonyon.api.response.SeatUsedEventResponse
import dev.yonyon.application.service.SeatUsedEventService
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
@Tag(name = "Seat")
@Controller("/api/seats")
@Secured(SecurityRule.IS_ANONYMOUS)
class SeatController(private val seatUsedEventService: SeatUsedEventService) {

    /**
     * 座席確保API
     *
     * @param seatId  座席ID
     * @return 座席使用イベントレスポンス
     */
    @Post("/{seat_id}/reserve")
    fun reserve(
        @PathVariable("seat_id") seatId: UUID
    ): SeatUsedEventResponse {
        val trackingId = seatUsedEventService.reserveSeat(seatId)
        return SeatUsedEventResponse(trackingId)
    }

    /**
     * 座席解放API
     *
     * @param seatId 座席ID
     */
    @Post("/{seat_id}/purge")
    fun purge(
        @PathVariable("seat_id") seatId: UUID,
        @Body requestBody: SeatPurgeRequest
    ) {
        seatUsedEventService.purgeSeat(seatId, requestBody.trackingId)
    }
}
