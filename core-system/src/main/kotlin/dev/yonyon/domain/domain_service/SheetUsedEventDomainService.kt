package dev.yonyon.domain.domain_service

import dev.yonyon.domain.event.SheetUsedEvent
import dev.yonyon.domain.repository.EventRepository
import jakarta.inject.Singleton
import java.util.*

@Singleton
class SheetUsedEventDomainService(
    private val eventRepository: EventRepository
) {
    fun save(sheetUsedEvent: SheetUsedEvent): Boolean {
        if (!checkConsistency(sheetUsedEvent.sheetId, sheetUsedEvent.trackingId, sheetUsedEvent.isUsed)) {
            return false
        }
        eventRepository.saveSheetUsedEvent(sheetUsedEvent)
        createSnapshot(sheetUsedEvent.sheetId, sheetUsedEvent.trackingId, sheetUsedEvent.isUsed)
        return true
    }

    private fun createSnapshot(sheetId: UUID, trackingId: UUID, isUsed: Boolean) {
        eventRepository.saveSheetSnapshot(sheetId, trackingId, isUsed)
    }

    private fun checkConsistency(sheetId: UUID, trackingId: UUID, isUsed: Boolean): Boolean {
        val sheetSnapshot = eventRepository.getSheetSnapshot(sheetId)
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
