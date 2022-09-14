package dev.yonyon.infrastructure.repository_impl

import dev.yonyon.domain.repository.SeatRepository
import dev.yonyon.infrastructure.table.SeatTable
import io.micronaut.context.annotation.Infrastructure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

@Infrastructure
class SeatRepositoryImpl(private val database: Database) : SeatRepository {

    override fun updateIsUsed(id: UUID, isUsed: Boolean) {
        transaction {
            addLogger(StdOutSqlLogger)
            SeatTable.update({ SeatTable.id eq id }) {
                it[SeatTable.isUsed] = isUsed
            }
        }
    }

}
