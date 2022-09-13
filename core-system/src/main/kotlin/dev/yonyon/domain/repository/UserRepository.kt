package dev.yonyon.domain.repository

import dev.yonyon.domain.model.UserModel

interface UserRepository {

    fun findByEmail(email: String): UserModel?

}
