@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.LocalTestDataServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class ParentSingleServices @Inject protected constructor(factory: ParentSingleEntityFactory) : LocalTestDataServices<ParentSingleEntity>({ factory.create(it) })

@Singleton
internal class SampleMultiServices @Inject protected constructor(factory: SampleMultiEntityFactory) : LocalTestDataServices<SampleMultiEntity>({ factory.create(it) })

@Singleton
internal class SampleManyUpdatesServices @Inject protected constructor(factory: SampleManyUpdatesEntityFactory) : LocalTestDataServices<SampleManyUpdatesEntity>({ factory.create(it) })

@Singleton
internal class SampleUpdateDifferentObjectsServices @Inject protected constructor(factory: SampleUpdateDifferentObjectsEntityFactory) : LocalTestDataServices<SampleUpdateDifferentObjectsEntity>({ factory.create(it) })

@Singleton
internal class SampleManyActionsSameTypeServices @Inject protected constructor(factory: SampleManyActionsSameTypeEntityFactory) : LocalTestDataServices<SampleManyActionsSameTypeEntity>({ factory.create(it) })

@Singleton
internal class SampleUpdateWithPreviousValueServices @Inject protected constructor(factory: SampleUpdateWithPreviousValueEntityFactory) : LocalTestDataServices<SampleUpdateWithPreviousValueEntity>({ factory.create(it) })

@Singleton
internal class SampleDefaultServices @Inject protected constructor(factory: SampleDefaultEntityFactory) : LocalTestDataServices<SampleDefaultEntity>({ factory.create(it) })