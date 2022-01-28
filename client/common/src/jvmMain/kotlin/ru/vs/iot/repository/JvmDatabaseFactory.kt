package ru.vs.iot.repository

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.kodein.di.DirectDI

private class JvmDatabaseFactoryImpl : DatabaseFactory {
    override fun createDatabase(): Database {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        return Database(driver)
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseFactory {
    return JvmDatabaseFactoryImpl()
}
