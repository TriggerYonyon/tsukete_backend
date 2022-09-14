package dev.yonyon.srevice

import dev.yonyon.domain.domain_service.SeatUsedEventDomainService
import dev.yonyon.domain.driver.MessageQueueDriver
import dev.yonyon.domain.repository.SeatRepository
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class SeatService(
    private val seatRepository: SeatRepository,
    private val messageQueueDriver: MessageQueueDriver,
    private val seatUsedEventDomainService: SeatUsedEventDomainService
) {

    private val logger = LoggerFactory.getLogger(SeatService::class.java)

    fun updateIsUsed() {
        val eventId = messageQueueDriver.receiveMessage()
        if (eventId != null) {
            val seatUsedEvent = seatUsedEventDomainService.getSeatUsedEvent(eventId)
            if (seatUsedEvent != null) {
                logger.info("converting event id: ${seatUsedEvent.id}")
                seatRepository.updateIsUsed(seatUsedEvent.seatId, seatUsedEvent.isUsed)
            }
        } else {
            logger.info("No convert seat used event")
        }
    }

}
