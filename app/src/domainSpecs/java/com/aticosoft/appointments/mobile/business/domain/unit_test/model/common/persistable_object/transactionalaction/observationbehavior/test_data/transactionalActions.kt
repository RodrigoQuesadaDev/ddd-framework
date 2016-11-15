package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestAbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestTransactionalUpdateAction
import com.rodrigodev.common.collection.synchronized
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal interface AbstractLocalTestTransactionalAction<E : AbstractTestData> : TestAbstractTransactionalAction<E>, WithChangeTypes {

    override fun resetConfiguration() {
    }

    //region Injection
    class InjectedMembers<E : AbstractTestData> @Inject protected constructor(
            val valueProducer: ValueProducer<E>
    )
    //endregion

    //region Value Producer Classes
    @Singleton
    class ValueProducer<E : AbstractTestData> @Inject protected constructor(
            // Code wont't compile without this.
            eventType: Class<E>
    ) {

        private val _producedValues = mutableListOf<ProducedValue>().synchronized()

        val producedValues: List<ProducedValue>
            get() = _producedValues

        fun TestTransactionalAction<E>.produce(value: Int) {
            _producedValues.add(ProducedValue(changeTypes, value))
        }

        fun TestTransactionalUpdateAction<E>.produce(previousValue: Int, currentValue: Int) {
            _producedValues.add(ProducedValue(changeTypes, currentValue, previousValue = previousValue))
        }

        fun clear(): Unit = _producedValues.clear()
    }

    data class ProducedValue(override val changeTypes: Array<out ObjectChangeType>, val value: Int, val previousValue: Int? = null) : WithChangeTypes
    //endregion
}

internal abstract class LocalTestTransactionalAction<E : AbstractTestData>(vararg changeTypes: ObjectChangeType) : TestTransactionalAction<E>(*changeTypes), AbstractLocalTestTransactionalAction<E> {

    private lateinit var m: AbstractLocalTestTransactionalAction.InjectedMembers<E>

    val specifiedChangeTypes = changeTypes

    override fun execute(value: E) {
        with(m.valueProducer) { produce(value.value) }
    }

    //region Injection
    @Inject
    fun inject(injectedMembers: AbstractLocalTestTransactionalAction.InjectedMembers<E>) {
        m = injectedMembers
    }
    //endregion
}

internal abstract class LocalTestTransactionalUpdateAction<E : AbstractTestData> : TestTransactionalUpdateAction<E>(), AbstractLocalTestTransactionalAction<E> {

    private lateinit var m: AbstractLocalTestTransactionalAction.InjectedMembers<E>

    override fun execute(previousValue: E, currentValue: E) {
        with(m.valueProducer) { produce(previousValue.value, currentValue.value) }
    }

    //region Injection
    @Inject
    fun inject(injectedMembers: AbstractLocalTestTransactionalAction.InjectedMembers<E>) {
        m = injectedMembers
    }
    //endregion
}

//region ParentSingleEntity
@Singleton
internal class ParentSingleTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<ParentSingleEntity>(ADD)

@Singleton
internal class ParentSingleTransactionalAction2 @Inject constructor() : LocalTestTransactionalAction<ParentSingleEntity>(UPDATE)

@Singleton
internal class ParentSingleTransactionalAction3 @Inject constructor() : LocalTestTransactionalAction<ParentSingleEntity>(REMOVE)
//endregion

//region ChildSingleEntity
@Singleton
internal class ChildSingleTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<ChildSingleEntity>(ADD)

@Singleton
internal class ChildSingleTransactionalAction2 @Inject constructor() : LocalTestTransactionalAction<ChildSingleEntity>(UPDATE)

@Singleton
internal class ChildSingleTransactionalAction3 @Inject constructor() : LocalTestTransactionalAction<ChildSingleEntity>(REMOVE)
//endregion

//region SampleMultiEntity
@Singleton
internal class SampleMultiTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<SampleMultiEntity>(ADD, REMOVE)

@Singleton
internal class SampleMultiTransactionalAction2 @Inject constructor() : LocalTestTransactionalAction<SampleMultiEntity>(ADD, UPDATE)

@Singleton
internal class SampleMultiTransactionalAction3 @Inject constructor() : LocalTestTransactionalAction<SampleMultiEntity>(UPDATE, REMOVE)

@Singleton
internal class SampleMultiTransactionalAction4 @Inject constructor() : LocalTestTransactionalAction<SampleMultiEntity>(ADD, UPDATE, REMOVE)
//endregion

//region SampleManyUpdatesEntity
@Singleton
internal class SampleManyUpdatesTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<SampleManyUpdatesEntity>(UPDATE)
//endregion

//region SampleUpdateDifferentObjectsEntity
@Singleton
internal class SampleUpdateDifferentObjectsTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<SampleUpdateDifferentObjectsEntity>(UPDATE)
//endregion

//region SampleManyActionsSameTypeEntity
@Singleton
internal class SampleManyActionsSameTypeTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<SampleManyActionsSameTypeEntity>(UPDATE)

@Singleton
internal class SampleManyActionsSameTypeTransactionalAction2 @Inject constructor() : LocalTestTransactionalAction<SampleManyActionsSameTypeEntity>(UPDATE)

@Singleton
internal class SampleManyActionsSameTypeTransactionalAction3 @Inject constructor() : LocalTestTransactionalAction<SampleManyActionsSameTypeEntity>(UPDATE)
//endregion

//region SampleUpdateWithPreviousValueEntity
@Singleton
internal class SampleUpdateWithPreviousValueTransactionalAction1 @Inject constructor() : LocalTestTransactionalUpdateAction<SampleUpdateWithPreviousValueEntity>()
//endregion

//region SampleDefaultEntity
@Singleton
internal class SampleDefaultTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<SampleDefaultEntity>()
//endregion

//region Other Classes
interface WithChangeTypes {
    val changeTypes: Array<out ObjectChangeType>
}
//endregion