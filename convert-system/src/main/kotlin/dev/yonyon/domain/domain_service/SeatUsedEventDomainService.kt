package dev.yonyon.domain.domain_service

import dev.yonyon.domain.event.SeatUsedEvent
import dev.yonyon.domain.repository.EventRepository
import jakarta.inject.Singleton
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.*

@Singleton
class SeatUsedEventDomainService(private val eventRepository: EventRepository) {

    fun getSeatUsedEvent(eventId: String): SeatUsedEvent? {
        val items = eventRepository.getSeatUsedEvent(eventId)
        if (items.isEmpty()) {
            return null
        }

        if (!checkItems(items)) {
            return null
        }

        return SeatUsedEvent(
            id = UUID.fromString(items["id"]!!.s()),
            trackingId = UUID.fromString(items["trackingId"]!!.s()),
            seatId = UUID.fromString(items["seatId"]!!.s()),
            isUsed = items["isUsed"]!!.bool()
        )
    }

    private fun checkItems(items: MutableMap<String, AttributeValue>): Boolean {
        items.keys.forEach {
            if (!items.containsKey(it) || items[it] == null) {
                return false
            }
        }
        return true
    }

}
