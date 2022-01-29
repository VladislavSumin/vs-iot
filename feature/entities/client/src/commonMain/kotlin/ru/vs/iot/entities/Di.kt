package ru.vs.iot.entities

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.entities.api.EntitiesApi
import ru.vs.iot.entities.api.EntitiesApiImpl
import ru.vs.iot.entities.domain.EntitiesInteractor
import ru.vs.iot.entities.domain.EntitiesInteractorImpl
import ru.vs.iot.entities.ui.EntitiesViewModel

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesCore())

    // Apis
    bindSingleton<EntitiesApi> { EntitiesApiImpl(i()) }

    // Interactors
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }

    // View models
    bindProvider { EntitiesViewModel(i(), i()) }
}
