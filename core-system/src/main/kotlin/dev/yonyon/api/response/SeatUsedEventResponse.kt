package dev.yonyon.api.response

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class SeatUsedEventResponse(
    val trackingId: UUID
)
