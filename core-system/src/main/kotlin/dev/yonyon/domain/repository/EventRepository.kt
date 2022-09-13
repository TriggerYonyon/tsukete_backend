package dev.yonyon.domain.repository

import dev.yonyon.domain.event.SheetUsedEvent
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.*

interface EventRepository {

    fun saveSheetUsedEvent(event: SheetUsedEvent)

    fun saveSheetSnapshot(sheetId: UUID, isUsed: Boolean)

    fun getSheetSnapshot(sheetId: UUID): MutableMap<String, AttributeValue>

}
