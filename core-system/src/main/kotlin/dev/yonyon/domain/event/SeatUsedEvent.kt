package dev.yonyon.domain.event

import java.time.OffsetDateTime
import java.util.*

class SeatUsedEvent(

    val id: UUID,

    val trackingId: UUID,

    val seatId: UUID,

    val isUsed: Boolean,

    val timeStump: OffsetDateTime

)
