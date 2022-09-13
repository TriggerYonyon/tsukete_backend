package dev.yonyon.api.exception_handler

import dev.yonyon.api.response.ErrorResponse
import dev.yonyon.exception.ErrorCode
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

/**
 * RuntimeExceptionハンドラー
 */
@Produces
@Singleton
@Requires(classes = [RuntimeException::class, ExceptionHandler::class])
class RuntimeExceptionHandler : ExceptionHandler<RuntimeException, HttpResponse<ErrorResponse>> {
    /**
     * RuntimeExceptionが発生した場合は500エラー
     */
    override fun handle(request: HttpRequest<*>, exception: RuntimeException): HttpResponse<ErrorResponse> {
        println(exception.message)
        val errorCode = ErrorCode.UNKNOWN_ERROR
        return HttpResponse.status<ErrorResponse?>(HttpStatus.INTERNAL_SERVER_ERROR) //
            .body(ErrorResponse(errorCode.code, errorCode.message))
    }
}
