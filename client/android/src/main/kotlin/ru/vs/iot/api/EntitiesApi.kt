package ru.vs.iot.api

import io.ktor.client.HttpClient

interface EntitiesApi

class EntitiesApiImpl(
    private val client: HttpClient

) : EntitiesApi
