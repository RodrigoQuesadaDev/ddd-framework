package com.aticosoft.appointments.mobile.business.test.specifications;

import com.aticosoft.appointments.mobile.business.AbstractApplication;
import com.aticosoft.appointments.mobile.business.ApplicationComponent;
import com.aticosoft.appointments.mobile.business.ApplicationModule;
import com.aticosoft.appointments.mobile.business.BuildConfig;
import com.aticosoft.appointments.mobile.business.domain.application.AppointmentService;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository;
import com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence.TestPersistenceConfigurer;
import com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence.TestPersistenceModule;
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareSpecification;
import com.aticosoft.appointments.mobile.business.test.specifications.BaseSpecification.TestApplication;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.TestLifecycleApplication;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rodrigo on 25/07/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, application = TestApplication.class)
public abstract class BaseSpecification implements AppointmentAwareSpecification {

    public static class TestApplication extends AbstractApplication<TestApplicationComponent> implements TestLifecycleApplication {

        @Override protected TestApplicationComponent createApplicationComponent() {
            return DaggerBaseSpecification_TestApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .persistenceModule(new TestPersistenceModule(new TestPersistenceConfigurer()))
                    .build();
        }

        @Override
        public void beforeTest(Method method) {
        }

        @Override
        @SuppressWarnings("unchecked")
        public void prepareTest(Object specification) {
            applicationComponent.inject((BaseSpecification) specification);
        }

        @Override
        public void afterTest(Method method) {
        }
    }

    @Singleton
    @Component(modules = ApplicationModule.class)
    public interface TestApplicationComponent<T extends BaseSpecification> extends ApplicationComponent {

        void inject(BaseSpecification specification);
    }

    @Override @Inject
    public void appointmentService(AppointmentService appointmentService) {
    }

    @Override @Inject
    public void appointmentRepository(AppointmentRepository appointmentRepository) {
    }
}
