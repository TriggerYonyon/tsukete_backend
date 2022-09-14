package dev.yonyon.domain.repository

import java.util.*

interface SeatRepository {

    fun updateIsUsed(id: UUID, isUsed: Boolean)

}
