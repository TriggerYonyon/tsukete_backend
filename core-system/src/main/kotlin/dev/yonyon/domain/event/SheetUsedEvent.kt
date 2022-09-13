package dev.yonyon.domain.event

import java.time.OffsetDateTime
import java.util.*

class SheetUsedEvent(

    val id: UUID,

    val trackingId: UUID,

    val sheetId: UUID,

    val isUsed: Boolean,

    val timeStump: OffsetDateTime

)
