package dev.yonyon.infrastructure.repository_impl

import dev.yonyon.domain.repository.EventRepository
import io.micronaut.context.annotation.Infrastructure
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest

@Infrastructure
class EventRepositoryImpl(
    private val dynamoDbClient: DynamoDbClient
) : EventRepository {

    override fun getSeatUsedEvent(id: String): MutableMap<String, AttributeValue> {
        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet["id"] = AttributeValue.fromS(id)

        val request = GetItemRequest.builder() //
            .key(keyToGet) //
            .tableName("SeatUsedEvent") //
            .build()
        return dynamoDbClient.getItem(request).item()
    }

}
