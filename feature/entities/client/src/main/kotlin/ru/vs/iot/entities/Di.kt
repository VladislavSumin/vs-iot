package ru.vs.iot.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inSet
import org.kodein.di.provider
import ru.vs.iot.di.Modules
import ru.vs.iot.di.bindViewModel
import ru.vs.iot.di.i
import ru.vs.iot.entities.api.EntitiesApi
import ru.vs.iot.entities.api.EntitiesApiImpl
import ru.vs.iot.entities.domain.EntitiesInteractor
import ru.vs.iot.entities.domain.EntitiesInteractorImpl
import ru.vs.iot.entities.ui.Entities
import ru.vs.iot.entities.ui.EntitiesScreen
import ru.vs.iot.entities.ui.EntitiesViewModel
import ru.vs.iot.navigation.ui.Screen
import ru.vs.iot.navigation.ui.destination.NavigationDestination
import ru.vs.iot.navigation.ui.destination.createDestination

fun Modules.featureEntities() = DI.Module("feature-entities") {
    // Apis
    bindSingleton<EntitiesApi> { EntitiesApiImpl(i()) }

    // Interactors
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }

    // View models
    bindViewModel { EntitiesViewModel(i(), i()) }

    // Navigation
    inSet<NavigationDestination> { provider { Screen.Entities.createDestination { EntitiesScreen() } } }
}
