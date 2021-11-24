package ru.vs.iot.ui.screen.entities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.vs.iot.compose.di.kodeinViewModel

@Composable
fun EntititesScreen(viewModel: EntitiesViewModel = kodeinViewModel()) {
    when (val state = viewModel.state.collectAsState().value) {
        is EntitiesScreenState.ShowEntities -> RenderEntities(state)
    }
}

@Composable
private fun RenderEntities(state: EntitiesScreenState.ShowEntities) {
    LazyColumn {
        items(state.entities) { entityItem ->
            RenderEntityItem(entityItem)
        }
    }
}

@Composable
private fun RenderEntityItem(entityItem: EntitiesScreenState.EntityItem) {
    Column {
        Text(entityItem.server.name)
        Text(entityItem.id.raw)
    }
}
