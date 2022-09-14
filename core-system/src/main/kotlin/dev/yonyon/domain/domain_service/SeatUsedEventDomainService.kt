package dev.yonyon.domain.domain_service

import dev.yonyon.domain.event.SeatUsedEvent
import dev.yonyon.domain.repository.EventRepository
import jakarta.inject.Singleton
import java.util.*

@Singleton
class SeatUsedEventDomainService(
    private val eventRepository: EventRepository
) {
    fun save(seatUsedEvent: SeatUsedEvent): Boolean {
        if (!checkConsistency(seatUsedEvent.seatId, seatUsedEvent.trackingId, seatUsedEvent.isUsed)) {
            return false
        }
        eventRepository.saveSeatUsedEvent(seatUsedEvent)
        createSnapshot(seatUsedEvent.seatId, seatUsedEvent.trackingId, seatUsedEvent.isUsed)
        return true
    }

    private fun createSnapshot(sheetId: UUID, trackingId: UUID, isUsed: Boolean) {
        eventRepository.saveSeatSnapshot(sheetId, trackingId, isUsed)
    }

    private fun checkConsistency(sheetId: UUID, trackingId: UUID, isUsed: Boolean): Boolean {
        val sheetSnapshot = eventRepository.getSeatSnapshot(sheetId)
        if (!sheetSnapshot.containsKey("isUsed")) {
            return true
        }

        if (sheetSnapshot["trackingId"]!!.s() != trackingId.toString()) {
            return false
        }

        if (sheetSnapshot["isUsed"]!!.bool() != isUsed) {
            return true
        }

        return false
    }
}
