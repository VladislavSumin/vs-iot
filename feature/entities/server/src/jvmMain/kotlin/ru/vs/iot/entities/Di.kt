package ru.vs.iot.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.iot.di.Modules
import ru.vs.iot.di.i
import ru.vs.iot.entities.domain.EntityInteractor
import ru.vs.iot.entities.domain.EntityInteractorImpl
import ru.vs.iot.entities.web.api.EntityApi

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesCore())

    // Interactors
    bindSingleton<EntityInteractor> { EntityInteractorImpl() }

    // Apis
    bindSingleton { EntityApi(i(), i()) }
}
