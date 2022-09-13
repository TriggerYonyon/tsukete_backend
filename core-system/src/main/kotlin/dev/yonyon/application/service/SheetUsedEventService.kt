package dev.yonyon.application.service

import dev.yonyon.domain.domain_service.SheetUsedEventDomainService
import dev.yonyon.domain.event.SheetUsedEvent
import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.UnexpectedException
import dev.yonyon.infrastructure.MessageQueueDriver
import jakarta.inject.Singleton
import java.time.OffsetDateTime
import java.util.*

@Singleton
class SheetUsedEventService(
    private val sheetUsedEventDomainService: SheetUsedEventDomainService,
    private val messageQueueDriver: MessageQueueDriver
) {

    fun reserveSheet(sheetId: UUID): UUID {
        val trackingId = UUID.randomUUID()
        val event = SheetUsedEvent(UUID.randomUUID(), trackingId, sheetId, true, OffsetDateTime.now())

        val isSuccess = sheetUsedEventDomainService.save(event)
        if (!isSuccess) {
            throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR)
        }
        messageQueueDriver.sendMessage(event.id.toString())

        return trackingId
    }

    fun purgeSheet(sheetId: UUID, trackingId: UUID) {
        val event = SheetUsedEvent(UUID.randomUUID(), trackingId, sheetId, false, OffsetDateTime.now())
        val isSuccess = sheetUsedEventDomainService.save(event)
        if (!isSuccess) {
            throw UnexpectedException(ErrorCode.UNEXPECTED_ERROR)
        }
        messageQueueDriver.sendMessage(event.id.toString())
    }

}
