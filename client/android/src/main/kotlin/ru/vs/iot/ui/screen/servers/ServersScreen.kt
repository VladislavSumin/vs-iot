package ru.vs.iot.ui.screen.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.vs.iot.di.kodeinViewModel
import ru.vs.iot.repository.Server
import ru.vs.iot.ui.core.LocalNavigation
import ru.vs.iot.ui.theme.ComposeDemoTheme

@Composable
fun ServersScreen(
    viewModel: ServersViewModel = kodeinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    when (state) {
        ServersScreenState.Loading -> RenderLoadingState()
        is ServersScreenState.ShowServersList -> RenderServerListState(state)
    }
}

@Composable
private fun RenderLoadingState() {
    // TODO add loader
}

@Composable
private fun RenderServerListState(state: ServersScreenState.ShowServersList) {
    val navigation = LocalNavigation.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigation.navigate("add-server")
                }
            ) {
                Text("+")
            }
        }
    ) {
        ServersList(state.servers)
    }
}

@Composable
private fun ServersList(servers: List<Server>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(servers, { it.id }) {
            ServerItem(it)
        }
    }
}

@Composable
private fun ServerItem(server: Server) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            Modifier
                .padding(6.dp, 3.dp)
        ) {
            Text(server.name, style = MaterialTheme.typography.h6)
            Text(server.url)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServersScreenPreview() {
    ComposeDemoTheme {
        ServersScreen()
    }
}
