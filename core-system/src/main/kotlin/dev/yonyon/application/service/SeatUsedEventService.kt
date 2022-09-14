package dev.yonyon.application.service

import dev.yonyon.domain.domain_service.SeatUsedEventDomainService
import dev.yonyon.domain.event.SeatUsedEvent
import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.UnexpectedException
import dev.yonyon.infrastructure.MessageQueueDriver
import jakarta.inject.Singleton
import java.time.OffsetDateTime
import java.util.*

@Singleton
class SeatUsedEventService(
    private val seatUsedEventDomainService: SeatUsedEventDomainService,
    private val messageQueueDriver: MessageQueueDriver
) {

    fun reserveSeat(seatId: UUID): UUID {
        val trackingId = UUID.randomUUID()
        val event = SeatUsedEvent(UUID.randomUUID(), trackingId, seatId, true, OffsetDateTime.now())

        val isSuccess = seatUsedEventDomainService.save(event)
        if (!isSuccess) {
            throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR)
        }
        messageQueueDriver.sendMessage(event.id.toString())

        return trackingId
    }

    fun purgeSeat(seatId: UUID, trackingId: UUID) {
        val event = SeatUsedEvent(UUID.randomUUID(), trackingId, seatId, false, OffsetDateTime.now())
        val isSuccess = seatUsedEventDomainService.save(event)
        if (!isSuccess) {
            throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR)
        }
        messageQueueDriver.sendMessage(event.id.toString())
    }

}
