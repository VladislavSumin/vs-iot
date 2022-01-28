package ru.vs.iot.repository

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.kodein.di.DirectDI
import org.kodein.di.instance

class AndroidDatabaseFactoryImpl(private val context: Context) : DatabaseFactory {
    override fun createDatabase(): Database {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "database.db")
        return Database(driver)
    }
}

internal actual fun DirectDI.createDatabaseFactory(): DatabaseFactory {
    return AndroidDatabaseFactoryImpl(instance()) // TODO i()
}
