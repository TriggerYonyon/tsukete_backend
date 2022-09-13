package dev.yonyon.infrastructure

import dev.yonyon.configuration.AwsConfiguration
import io.micronaut.context.annotation.Infrastructure
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.util.UUID

@Infrastructure
class MessageQueueDriver(
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration
) {

    fun sendMessage(message: String) {
        val request = SendMessageRequest.builder() //
            .queueUrl(awsConfiguration.sqsEndpoint) //
            .messageBody(message) //
            .messageGroupId(UUID.randomUUID().toString()) //
            .messageDeduplicationId(UUID.randomUUID().toString()) //
            .build()

        sqsClient.sendMessage(request)
    }

}
