package dev.yonyon.api.request

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class SheetPurgeRequest(
    val trackingId: UUID
)
