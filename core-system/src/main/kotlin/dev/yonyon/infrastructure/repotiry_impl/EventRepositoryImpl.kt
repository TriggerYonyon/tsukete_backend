package dev.yonyon.infrastructure.repotiry_impl

import dev.yonyon.domain.event.SeatUsedEvent
import dev.yonyon.domain.repository.EventRepository
import io.micronaut.context.annotation.Infrastructure
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.*

@Infrastructure
class EventRepositoryImpl(private val dynamoDbClient: DynamoDbClient) : EventRepository {

    override fun saveSeatUsedEvent(event: SeatUsedEvent) {
        val items = mutableMapOf<String, AttributeValue>()
        items["id"] = AttributeValue.fromS(event.id.toString())
        items["trackingId"] = AttributeValue.fromS(event.trackingId.toString())
        items["seatId"] = AttributeValue.fromS(event.seatId.toString())
        items["isUsed"] = AttributeValue.fromBool(event.isUsed)
        items["timeStump"] = AttributeValue.fromS(event.timeStump.toString())

        val request = PutItemRequest.builder() //
            .item(items) //
            .tableName("SeatUsedEvent") //
            .build()
        dynamoDbClient.putItem(request)
    }

    override fun saveSeatSnapshot(seatId: UUID, trackingId: UUID, isUsed: Boolean) {
        val itemKey = mutableMapOf<String, AttributeValue>()
        itemKey["id"] = AttributeValue.fromS(seatId.toString())

        val updatedItems = mutableMapOf<String, AttributeValueUpdate>()
        updatedItems["isUsed"] = AttributeValueUpdate.builder() //
            .value(AttributeValue.fromBool(isUsed)) //
            .action(AttributeAction.PUT) //
            .build()
        updatedItems["trackingId"] = AttributeValueUpdate.builder() //
            .value(AttributeValue.fromS(trackingId.toString())) //
            .action(AttributeAction.PUT) //
            .build()

        val request = UpdateItemRequest.builder() //
            .tableName("SeatSnapshot") //
            .key(itemKey) //
            .attributeUpdates(updatedItems) //
            .build()

        dynamoDbClient.updateItem(request)
    }

    override fun getSeatSnapshot(seatId: UUID): MutableMap<String, AttributeValue> {
        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet["id"] = AttributeValue.fromS(seatId.toString())

        val request = GetItemRequest.builder() //
            .tableName("SeatSnapshot") //
            .key(keyToGet) //
            .build()

        val response = dynamoDbClient.getItem(request)
        return response.item()
    }

}
