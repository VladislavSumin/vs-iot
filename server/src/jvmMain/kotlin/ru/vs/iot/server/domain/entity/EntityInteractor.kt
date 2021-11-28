package ru.vs.iot.server.domain.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.vs.iot.entities.dto.entity_state.EntityStateDTO
import ru.vs.iot.entities.dto.entity_state.primitive.BooleanEntityState
import ru.vs.iot.entities.dto.entity_state.primitive.StringEntityState
import ru.vs.iot.id.Id
import kotlin.random.Random

interface EntityInteractor {
    fun observeEntities(): Flow<Map<Id, Entity>>
    fun observeEntitiesState(): Flow<Map<Id, EntityState>>
}

class EntityInteractorImpl : EntityInteractor {
    private val entities = mutableMapOf<Id, Entity>()
    private val entitiesFlow = MutableStateFlow(entities)

    init {
        entities.add(BooleanEntity(Id("entity/boolean1")))
        entities.add(BooleanEntity(Id("entity/boolean2")))
        entities.add(BooleanEntity(Id("entity/boolean3")))
        entities.add(BooleanEntity(Id("entity/boolean4")))
        entities.add(BooleanEntity(Id("entity/boolean5")))
        entities.add(MutableBooleanEntity(Id("entity/mut_boolean1")))
        entities.add(MutableBooleanEntity(Id("entity/mut_boolean2")))
        entities.add(MutableBooleanEntity(Id("entity/mut_boolean3")))
        entities.add(MutableBooleanEntity(Id("entity/mut_boolean4")))
        entities.add(MutableBooleanEntity(Id("entity/mut_boolean5")))
        entities.add(StringEntity(Id("entity/string1")))
        entities.add(StringEntity(Id("entity/string2")))
        entities.add(StringEntity(Id("entity/string3")))
        entities.add(StringEntity(Id("entity/string4")))
        entities.add(StringEntity(Id("entity/string5")))
        entities.add(MutableStringEntity(Id("entity/mut_string1")))
        entities.add(MutableStringEntity(Id("entity/mut_string2")))
        entities.add(MutableStringEntity(Id("entity/mut_string3")))
        entities.add(MutableStringEntity(Id("entity/mut_string4")))
        entities.add(MutableStringEntity(Id("entity/mut_string5")))
    }

    override fun observeEntities(): Flow<Map<Id, Entity>> = entitiesFlow

    // TODO вынести в SharedFlow что бы не вичислять одно и тоже слишком часто
    override fun observeEntitiesState(): Flow<Map<Id, EntityState>> = entitiesFlow.flatMapLatest { entities ->
        combine(entities.values.map { entity -> entity.state.map { entity.id to it } }) { it.toMap() }
    }

    private fun MutableMap<Id, Entity>.add(entity: Entity) {
        this[entity.id] = entity
    }
}

open class BooleanEntity(override val id: Id) : Entity {
    override val state: StateFlow<BooleanEntityState> = MutableStateFlow(BooleanEntityState(Random.nextBoolean()))
}

class MutableBooleanEntity(id: Id) : BooleanEntity(id)

open class StringEntity(override val id: Id) : Entity {
    override val state: StateFlow<StringEntityState> =
        MutableStateFlow(StringEntityState(Random.nextDouble().toString()))
}

class MutableStringEntity(id: Id) : StringEntity(id)

interface Entity {
    val id: Id
    val state: StateFlow<EntityState>
}

typealias EntityState = EntityStateDTO
