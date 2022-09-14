package dev.yonyon.domain.driver

interface MessageQueueDriver {

    fun receiveMessage(): String?

}
