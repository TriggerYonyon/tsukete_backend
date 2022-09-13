package dev.yonyon.configuration

import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton

@Singleton
class AwsConfiguration(
    @Property(name = "aws.accessKeyId")
    val accessKeyId: String,

    @Property(name = "aws.secretAccessKey")
    val secretAccessKey: String,

    @Property(name = "aws.dynamoDB.endpoint")
    val dynamoDBEndpoint: String,

    @Property(name = "aws.sqs.endpoint")
    val sqsEndpoint: String
)
