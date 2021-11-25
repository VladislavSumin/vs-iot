package ru.vs.iot.servers.dto

import kotlinx.serialization.Serializable

@Serializable
data class AboutServerDTO constructor(
    val version: String
)
