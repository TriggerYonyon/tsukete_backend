package dev.yonyon.factory

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource


/**
 * データベースクライアントファクトリー
 */
@Factory
class DbClientFactory(private val dataSource: DataSource) {

    /**
     *  データベースクライアントを取得
     *
     *  @return データベースクライアント
     */
    @Singleton
    fun getDbClient(): Database {
        return Database.connect(dataSource)
    }

}
