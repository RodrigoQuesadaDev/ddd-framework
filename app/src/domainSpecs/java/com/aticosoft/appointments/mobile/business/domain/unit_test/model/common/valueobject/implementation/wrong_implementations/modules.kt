package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.ValueObjects
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@Module
internal class ValueObjectsModule {

    @Provides @ElementsIntoSet @ValueObjects
    fun provideValueObjectsIntoSet(): Set<Class<*>> = setOf(
            TransientFieldParentValueObject::class.java,
            DelegatedFieldParentValueObject::class.java,
            NonAnnotatedCorrectChildWithTransientFieldParentValueObject::class.java,
            NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject::class.java,
            NonAnnotatedTransientFieldChildWithCorrectParentValueObject::class.java,
            NonAnnotatedDelegatedFieldChildWithCorrectParentValueObject::class.java,
            CorrectChildWithTransientFieldNonValueObjectParent::class.java,
            CorrectChildWithDelegatedFieldNonValueObjectParent::class.java
    )
}
