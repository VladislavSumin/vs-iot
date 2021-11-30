package ru.vs.iot.id

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// TODO с интерфейсом не подхватывается кастомный сериализатор
@Suppress("UnnecessaryAbstractClass")
@Serializable(with = IdAsStringSerializer::class)
abstract class Id internal constructor() {
    companion object {
        operator fun invoke(raw: String): Id = IdImpl(raw)
    }
}

@Serializable(with = IdAsStringSerializer::class)
internal class IdImpl(val raw: String) : Id() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as IdImpl

        if (raw != other.raw) return false

        return true
    }

    override fun hashCode(): Int {
        return raw.hashCode()
    }

    override fun toString(): String {
        return "IdImpl(raw=$raw)"
    }
}

internal object IdAsStringSerializer : KSerializer<Id> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("id", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Id {
        val raw = decoder.decodeString()
        return Id(raw)
    }

    override fun serialize(encoder: Encoder, value: Id) {
        value as IdImpl
        encoder.encodeString(value.raw)
    }
}
