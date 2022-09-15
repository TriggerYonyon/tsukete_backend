package dev.yonyon.infrastructure.driver_impl

import dev.yonyon.configuration.AwsConfiguration
import dev.yonyon.domain.driver.MessageQueueDriver
import io.micronaut.context.annotation.Factory
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest

@Factory
class MessageQueueDriverImpl(
    private val sqsClient: SqsClient,
    private val awsConfiguration: AwsConfiguration
) : MessageQueueDriver {

    override fun receiveMessage(): String? {
        val receiveRequest = ReceiveMessageRequest.builder() //
            .queueUrl(awsConfiguration.sqsQueueUrl) //
            .maxNumberOfMessages(1) //
            .build()

        val response = sqsClient.receiveMessage(receiveRequest)
        if (!response.hasMessages()) {
            return null
        }
        if (response.messages()[0] == null) {
            return null
        }

        val deleteRequest = DeleteMessageRequest.builder() //
            .queueUrl(awsConfiguration.sqsEndpoint) //
            .receiptHandle(response.messages()[0].receiptHandle()) //
            .build()
        sqsClient.deleteMessage(deleteRequest)


        return response.messages()[0].body()
    }

}
