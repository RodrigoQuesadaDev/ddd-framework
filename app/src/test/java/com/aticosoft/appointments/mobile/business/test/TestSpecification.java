package com.aticosoft.appointments.mobile.business.test;

import com.aticosoft.appointments.mobile.business.BuildConfig;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by rodrigo on 25/07/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public abstract class TestSpecification {
}
