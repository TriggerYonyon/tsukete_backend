package dev.yonyon.util

import jakarta.inject.Singleton
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Singleton
class AuthUtil {

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    fun encode(password: String): String {
        return bCryptPasswordEncoder.encode(password)
    }

    fun verify(rawPassword: String, encodedPassword: String): Boolean {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword)
    }

}
