package ru.vs.iot.entities.dto.entity_state.primitive

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vs.iot.entities.dto.entity_state.EntityStateDTO

@Serializable
@SerialName("primitive/string")
data class StringEntityState(val state: String) : EntityStateDTO
