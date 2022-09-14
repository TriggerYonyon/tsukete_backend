package dev.yonyon.api.request

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class SeatPurgeRequest(
    val trackingId: UUID
)
