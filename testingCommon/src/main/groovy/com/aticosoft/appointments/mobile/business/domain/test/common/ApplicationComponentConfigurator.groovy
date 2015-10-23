package com.aticosoft.appointments.mobile.business.domain.test.common

/**
 * Created by rodrigo on 23/10/15.
 */
class ApplicationComponentConfigurator {

    def configure(Class<?> applicationComponentBuilderClass, applicationModule, testModules) {
        return applicationComponentBuilderClass.builder()
                .applicationModule(applicationModule)
                .persistenceModule(testModules.persistenceModule)
                .entityObserversModule(testModules.entityObserversModule)
                .domainCommonModule(testModules.domainCommonModule)
                .build()
    }
}
