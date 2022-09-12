package dev.yonyon.exception

import io.micronaut.http.HttpStatus

open class BaseException(
    open val errorCode: ErrorCode,
    open val status: HttpStatus
) : RuntimeException()
