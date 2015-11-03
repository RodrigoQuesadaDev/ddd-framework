package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer.TestEntityListenersModule

/**
* Created by Rodrigo Quesada on 23/10/15.
*/
internal interface TestApplicationComponent<in S : DomainStory> : ApplicationComponent {

    fun inject(application: TestApplication)
    fun inject(story: S)
    fun inject(testEntityObserversModule: TestEntityListenersModule)
    fun inject(testDomainCommonModule: TestDomainCommonModule)
}