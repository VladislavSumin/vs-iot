package ru.vs.iot.repository

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


interface ServersRepository {
    fun observeServers(): Flow<List<Server>>
}

class ServersRepositoryImpl(private val serverQueries: ServerQueries) : ServersRepository {
    override fun observeServers(): Flow<List<Server>> {
        return serverQueries.selectAll().asFlow().mapToList(Dispatchers.IO)
    }
}
