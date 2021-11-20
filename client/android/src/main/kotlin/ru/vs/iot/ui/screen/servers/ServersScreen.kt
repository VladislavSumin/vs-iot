package ru.vs.iot.ui.screen.servers

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
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.vs.iot.di.kodeinViewModel
import ru.vs.iot.navigation.ui.LocalNavigation
import ru.vs.iot.repository.Server
import ru.vs.iot.ui.core.navigation.Screen
import ru.vs.iot.ui.theme.ComposeDemoTheme
import ru.vs.iot.ui.theme.NONE
import ru.vs.iot.ui.theme.Shapes

@Composable
fun ServersScreen(
    viewModel: ServersViewModel = kodeinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    when (state) {
        ServersScreenState.Loading -> RenderLoadingState()
        is ServersScreenState.ShowServersList -> RenderServerListState(state, viewModel)
    }
}

@Composable
private fun RenderLoadingState() {
    // TODO add loader
}

@Composable
private fun RenderServerListState(state: ServersScreenState.ShowServersList, viewModel: ServersViewModel) {
    val navigation = LocalNavigation.current
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navigation.navigate(Screen.AddServer)
        }) {
            Text("+")
        }
    }) {
        ServersList(state, viewModel)
    }
}

@Composable
private fun ServersList(state: ServersScreenState.ShowServersList, viewModel: ServersViewModel) {
    val servers = state.servers
    SwipeRefresh(state = rememberSwipeRefreshState(state.isRefreshing), onRefresh = { viewModel.onRefresh() }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(0.dp, 8.dp)
        ) {
            items(servers, { it.server.id }) {
                ServerItem(it, viewModel)
            }
        }
    }
}

@Composable
private fun ServerItem(serverState: ServersScreenState.ServerState, viewModel: ServersViewModel) {
    val server = serverState.server
    val connectivityState = serverState.connectivityState

    Card(modifier = Modifier.fillMaxWidth(), shape = Shapes.NONE) {
        Column(Modifier.padding(10.dp, 0.dp)) {
            Row {
                Text(
                    server.name,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically).weight(1f)
                )
                RenderServerItemMenu(server, viewModel)
            }
            Divider()
            Column(Modifier.padding(0.dp, 12.dp)) {
                Text(server.url)
                RenderServerConnectivityState(connectivityState)
            }
        }
    }
}

@Composable
private fun RenderServerItemMenu(server: Server, viewModel: ServersViewModel) {
    val (showPopupMenu, setShowPopupMenu) = remember { mutableStateOf(false) }

    IconButton(onClick = { setShowPopupMenu(true) }) {
        Icon(Icons.Filled.MoreVert, "Options")
        DropdownMenu(expanded = showPopupMenu, onDismissRequest = { setShowPopupMenu(false) }) {
            DropdownMenuItem(onClick = { viewModel.onClickDeleteServer(server) }) {
                Icon(Icons.Filled.Delete, "Delete")
                Text("Delete")
            }
        }
    }
}

@Composable
private fun RenderServerConnectivityState(connectivityState: ServersScreenState.ServerConnectivityState) {
    when (connectivityState) {
        ServersScreenState.ServerConnectivityState.CheckingConnectivity -> Text("Connecting...")
        is ServersScreenState.ServerConnectivityState.Error -> Text("Error: ${connectivityState.e}")
        is ServersScreenState.ServerConnectivityState.Success -> {
            Text("Success. Server version: ${connectivityState.aboutServer.version}")
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
