package dev.yonyon.application.provider

import dev.yonyon.domain.repository.UserRepository
import dev.yonyon.exception.ErrorCode
import dev.yonyon.exception.NotFoundException
import dev.yonyon.exception.UnauthorizedException
import dev.yonyon.util.AuthUtil
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword(
    private val userRepository: UserRepository,
    private val authUtil: AuthUtil
) : AuthenticationProvider {

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        if (authenticationRequest == null) {
            throw UnauthorizedException(ErrorCode.INVALID_CERTIFICATIONS)
        }
        val user = userRepository.findByEmail(authenticationRequest.identity as String) //
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER)

        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            if (authUtil.verify(authenticationRequest.secret as String, user.password)) {
                emitter.next(AuthenticationResponse.success(user.id.toString()))
                emitter.complete()
            } else {
                emitter.error(UnauthorizedException(ErrorCode.INVALID_CERTIFICATIONS))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }

}
