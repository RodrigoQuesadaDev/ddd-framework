package com.aticosoft.appointments.mobile.business.domain.specs

import com.aticosoft.appointments.mobile.business.BuildConfig
import com.rodrigodev.common.spec.story.SpecStory
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

/**
* Created by Rodrigo Quesada on 17/09/15.
*/
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(19))
internal abstract class DomainStory : SpecStory()