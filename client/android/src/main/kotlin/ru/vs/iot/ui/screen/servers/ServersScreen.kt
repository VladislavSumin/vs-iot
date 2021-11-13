package ru.vs.iot.ui.screen.servers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.vs.iot.di.kodeinViewModel
import ru.vs.iot.domain.Server
import ru.vs.iot.ui.theme.ComposeDemoTheme

@Composable
fun ServersScreen(
    viewModel: ServersViewModel = kodeinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    when (state) {
        ServersScreenState.Loading -> {}
        is ServersScreenState.ShowServersList -> ServersList(state.servers)
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
    Column(
        modifier = Modifier
            .padding(6.dp, 3.dp)
            .clickable { }
    ) {
        Text(server.name, style = MaterialTheme.typography.h6)
        Text(server.address)
    }
}

@Preview(showBackground = true)
@Composable
fun ServersScreenPreview() {
    ComposeDemoTheme {
        ServersScreen()
    }
}