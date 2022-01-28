package ru.vs.iot.servers.repository

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

internal interface ServersRepository {
    fun observeServers(): Flow<List<Server>>
    suspend fun insert(server: Server)
    suspend fun delete(server: Server)
}

internal class ServersRepositoryImpl(private val serverQueries: ServerQueries) : ServersRepository {
    override fun observeServers(): Flow<List<Server>> {
        return serverQueries.selectAll().asFlow().mapToList(/*Dispatchers.IO*/)
    }

    override suspend fun insert(server: Server) /*= withContext(Dispatchers.IO)*/ {
        check(server.id == 0L)
        serverQueries.insert(server)
    }

    override suspend fun delete(server: Server) /*= withContext(Dispatchers.IO)*/ {
        serverQueries.delete(server.id)
    }
}
