package ru.vs.iot.servers.ui.server

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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.push
import ru.vs.iot.decompose.view_model.decomposeViewModel
import ru.vs.iot.navigation.ui.LocalNavigation2
import ru.vs.iot.servers.repository.Server
import ru.vs.iot.servers.ui.AddServerScreen
import ru.vs.iot.uikit.theme.NONE
import ru.vs.iot.uikit.theme.Shapes
import ru.vs.iot.uikit.view.UKWaiting

@Composable
internal fun ServersScreenView(
    viewModel: ServersViewModel = decomposeViewModel()
) {
    when (val state = viewModel.state.collectAsState().value) {
        ServersScreenState.Loading -> UKWaiting()
        is ServersScreenState.ShowServersList -> RenderServerListState(state, viewModel)
    }
}

@Composable
private fun RenderServerListState(state: ServersScreenState.ShowServersList, viewModel: ServersViewModel) {
    val navigation = LocalNavigation2.current
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navigation.push(AddServerScreen) }) {
            Text("+")
        }
    }) {
        ServersList(state, viewModel::onRefresh, viewModel::onClickDeleteServer)
    }
}

@Composable
private fun ServersList(
    state: ServersScreenState.ShowServersList,
    onRefresh: () -> Unit,
    onClickDelete: (Server) -> Unit
) {
    val servers = state.servers
//    SwipeRefresh(state = rememberSwipeRefreshState(state.isRefreshing), onRefresh = onRefresh) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(0.dp, 8.dp)
    ) {
        items(servers, { it.server.id }) {
            ServerItem(it, onClickDelete)
        }
    }
//    }
}

@Composable
private fun ServerItem(serverState: ServersScreenState.ServerState, onClickDelete: (Server) -> Unit) {
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
                RenderServerItemMenu(onClickDelete = { onClickDelete(server) })
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
private fun RenderServerItemMenu(onClickDelete: () -> Unit) {
    var showPopupMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showPopupMenu = true }) {
        Icon(Icons.Filled.MoreVert, "Options")
//        DropdownMenu(expanded = showPopupMenu, onDismissRequest = { showPopupMenu = false }) {
//            DropdownMenuItem(onClick = onClickDelete) {
//                Icon(Icons.Filled.Delete, "Delete")
//                Text("Delete")
//            }
//        }
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
