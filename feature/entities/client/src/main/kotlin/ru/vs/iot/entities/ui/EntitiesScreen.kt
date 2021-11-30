package ru.vs.iot.entities.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vs.iot.compose.di.kodeinViewModel
import ru.vs.iot.entities.dto.entity.EntityDTO
import ru.vs.iot.entities.dto.entity_state.primitive.BooleanEntityState
import ru.vs.iot.entities.dto.entity_state.primitive.StringEntityState
import ru.vs.iot.id.Id
import ru.vs.iot.servers.repository.Server
import ru.vs.iot.uikit.theme.MainTheme
import ru.vs.iot.uikit.theme.NONE
import ru.vs.iot.uikit.theme.Shapes

@Composable
internal fun EntitiesScreen(viewModel: EntitiesViewModel = kodeinViewModel()) {
    when (val state = viewModel.state.collectAsState().value) {
        is EntitiesScreenState.ShowEntities -> RenderEntities(state)
    }
}

@Composable
private fun RenderEntities(state: EntitiesScreenState.ShowEntities) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(0.dp, 8.dp)
    ) {
        items(state.entities) { entityItem ->
            RenderEntityItem(entityItem)
        }
    }
}

@Composable
private fun RenderEntityItem(entityItem: EntitiesScreenState.EntityItem) {
    Card(modifier = Modifier.fillMaxWidth(), shape = Shapes.NONE) {
        Row(Modifier.padding(10.dp, 12.dp)) {
            Column(Modifier.weight(1f)) {
                Text(entityItem.id.raw, style = MaterialTheme.typography.h6)
                Text(entityItem.server.name)
            }

            when (val state = entityItem.entityState) {
                is BooleanEntityState -> Checkbox(state.state, {})
                is StringEntityState -> Text(state.state)
                else -> Text(state.toString())
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun EntityItemPreview() {
    MainTheme {
        RenderEntityItem(
            EntitiesScreenState.EntityItem(
                Server(0L, "Server name", "https://server.url.ru:443"),
                Id("entity/id"),
                EntityDTO(),
                StringEntityState("Entity state")
            )
        )
    }
}
