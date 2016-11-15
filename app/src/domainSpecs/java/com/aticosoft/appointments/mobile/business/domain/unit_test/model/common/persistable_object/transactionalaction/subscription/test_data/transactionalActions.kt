package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestTransactionalAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestTransactionalAction<E : AbstractTestData>(val id: Int) : TestTransactionalAction<E>() {

    override fun resetConfiguration() {
    }

    override fun execute(value: E) {
    }
}

//region OneSubscriptionEntity
@Singleton
internal class OneSubscriptionTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<OneSubscriptionEntity>(1)
//endregion

//region FiveSubscriptionsEntity
@Singleton
internal class FiveSubscriptionsTransactionalAction1 @Inject constructor() : LocalTestTransactionalAction<FiveSubscriptionsEntity>(1)

@Singleton
internal class FiveSubscriptionsTransactionalAction2 @Inject constructor() : LocalTestTransactionalAction<FiveSubscriptionsEntity>(2)

@Singleton
internal class FiveSubscriptionsTransactionalAction3 @Inject constructor() : LocalTestTransactionalAction<FiveSubscriptionsEntity>(3)

@Singleton
internal class FiveSubscriptionsTransactionalAction4 @Inject constructor() : LocalTestTransactionalAction<FiveSubscriptionsEntity>(4)

@Singleton
internal class FiveSubscriptionsTransactionalAction5 @Inject constructor() : LocalTestTransactionalAction<FiveSubscriptionsEntity>(5)
//endregion