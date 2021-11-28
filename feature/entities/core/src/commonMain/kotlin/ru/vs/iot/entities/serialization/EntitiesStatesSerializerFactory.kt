package ru.vs.iot.entities.serialization

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.iot.entities.dto.entity_state.EntityStateDTO
import ru.vs.iot.entities.dto.entity_state.primitive.BooleanEntityState
import ru.vs.iot.entities.dto.entity_state.primitive.StringEntityState

internal interface EntitiesStatesSerializerFactory {
    fun createSerializer(): SerializersModule
}

internal class EntitiesStatesSerializerFactoryImpl : EntitiesStatesSerializerFactory {
    override fun createSerializer() = SerializersModule {
        polymorphic(EntityStateDTO::class) {
            subclass(BooleanEntityState::class)
            subclass(StringEntityState::class)
        }
    }
}
