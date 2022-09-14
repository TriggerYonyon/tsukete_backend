package dev.yonyon.domain.repository

import dev.yonyon.domain.event.SeatUsedEvent
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.*

interface EventRepository {

    fun saveSeatUsedEvent(event: SeatUsedEvent)

    fun saveSeatSnapshot(seatId: UUID, trackingId: UUID, isUsed: Boolean)

    fun getSeatSnapshot(seatId: UUID): MutableMap<String, AttributeValue>

}
