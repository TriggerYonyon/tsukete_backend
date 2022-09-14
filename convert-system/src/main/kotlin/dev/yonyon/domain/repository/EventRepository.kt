package dev.yonyon.domain.repository

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

interface EventRepository {

    fun getSeatUsedEvent(id: String): MutableMap<String, AttributeValue>

}
