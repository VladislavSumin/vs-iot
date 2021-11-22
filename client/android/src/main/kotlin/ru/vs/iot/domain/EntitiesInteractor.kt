package ru.vs.iot.domain

import ru.vs.iot.api.EntitiesApi

interface EntitiesInteractor

class EntitiesInteractorImpl(
    private val api: EntitiesApi
) : EntitiesInteractor
