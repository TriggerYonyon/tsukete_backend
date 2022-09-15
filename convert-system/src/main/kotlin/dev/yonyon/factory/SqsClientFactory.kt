package dev.yonyon.factory

import dev.yonyon.configuration.AwsConfiguration
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Factory
class SqsClientFactory(private val awsConfiguration: AwsConfiguration) {

    @Singleton
    fun getSqsClient(): SqsClient {
        return SqsClient.builder() //
            .endpointOverride(URI(awsConfiguration.sqsEndpoint)) //
            .region(Region.AP_NORTHEAST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsConfiguration.accessKeyId, awsConfiguration.secretAccessKey)
                )
            ).build()
    }

}
