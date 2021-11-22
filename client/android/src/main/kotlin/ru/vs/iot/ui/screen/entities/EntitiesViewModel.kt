package ru.vs.iot.ui.screen.entities

import androidx.lifecycle.ViewModel
import ru.vs.iot.domain.EntitiesInteractor

class EntitiesViewModel(
    private val entitiesInteractor: EntitiesInteractor
) : ViewModel()
