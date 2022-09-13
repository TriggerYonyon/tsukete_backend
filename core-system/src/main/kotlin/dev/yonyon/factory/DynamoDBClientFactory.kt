package dev.yonyon.factory

import dev.yonyon.configuration.AwsConfiguration
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Factory
class DynamoDBClientFactory(
    private val awsConfiguration: AwsConfiguration
) {

    @Singleton
    fun getDynamoDBClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .endpointOverride(URI.create(awsConfiguration.dynamoDBEndpoint))
            .region(Region.AP_NORTHEAST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsConfiguration.accessKeyId, awsConfiguration.secretAccessKey)
                )
            ).build()
    }

}
