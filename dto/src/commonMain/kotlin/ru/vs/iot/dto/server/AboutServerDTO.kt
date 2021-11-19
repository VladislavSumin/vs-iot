package ru.vs.iot.dto.server

import kotlinx.serialization.Serializable

@Serializable
data class AboutServerDTO constructor(
    val version: String
)
