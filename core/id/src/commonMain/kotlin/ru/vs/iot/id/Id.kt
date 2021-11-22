package ru.vs.iot.id

interface Id {
    val raw: String

    companion object {
        operator fun invoke(raw: String): Id = IdImpl(raw)
    }
}

internal class IdImpl(override val raw: String) : Id
