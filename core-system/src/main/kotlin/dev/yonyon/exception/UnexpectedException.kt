package dev.yonyon.exception

import io.micronaut.http.HttpStatus

class UnexpectedException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.INTERNAL_SERVER_ERROR)
