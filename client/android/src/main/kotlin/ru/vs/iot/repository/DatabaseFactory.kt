package ru.vs.iot.repository

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

internal interface DatabaseFactory {
    fun createDatabase(): Database
}

class DatabaseFactoryImpl(private val context: Context) : DatabaseFactory {
    override fun createDatabase(): Database {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "database.db")
        return Database(driver)
    }
}
