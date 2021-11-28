package ru.vs.iot.entities.dto.entity_state.primitive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vs.iot.entities.dto.entity_state.EntityStateDTO

@Serializable
@SerialName("primitive/boolean")
data class BooleanEntityState(val state: Boolean) : EntityStateDTO
