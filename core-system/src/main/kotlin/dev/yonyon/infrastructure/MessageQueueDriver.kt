package dev.yonyon.infrastructure

import dev.yonyon.configuration.AwsConfiguration
import io.micronaut.context.annotation.Infrastructure
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Infrastructure
class MessageQueueDriver(
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration
) {

    fun sendMessage(message: String) {
        val request = SendMessageRequest.builder() //
            .queueUrl(awsConfiguration.sqsEndpoint) //
            .messageBody(message) //
            .messageGroupId("seat-used-event") //
            .build()

        sqsClient.sendMessage(request)
    }

}
