package dev.yonyon.domain.repository

import java.util.*

interface SeatRepository {

    fun existById(id: UUID): Boolean

}
