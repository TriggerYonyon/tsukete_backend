package dev.yonyon.infrastructure.repotiry_impl

import dev.yonyon.domain.repository.SeatRepository
import dev.yonyon.infrastructure.table.SeatTable
import io.micronaut.context.annotation.Infrastructure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

@Infrastructure
class SeatRepositoryImpl(private val database: Database) : SeatRepository {

    override fun existById(id: UUID): Boolean {
        transaction {
            addLogger(StdOutSqlLogger)
            SeatTable.select {
                SeatTable.id eq id
            }.singleOrNull()
        } ?: return false

        return true
    }

}
