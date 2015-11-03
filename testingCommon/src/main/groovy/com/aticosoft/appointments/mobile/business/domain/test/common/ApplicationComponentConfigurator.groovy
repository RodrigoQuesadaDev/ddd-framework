package com.aticosoft.appointments.mobile.business.domain.test.common

/**
*  Created by Rodrigo Quesada on 23/10/15.
*/
class ApplicationComponentConfigurator {

    def configure(Class<?> applicationComponentBuilderClass, applicationModule, testModules) {
        return applicationComponentBuilderClass.builder()
                .applicationModule(applicationModule)
                .persistenceModule(testModules.persistenceModule)
                .entityListenersModule(testModules.entityListenersModule)
                .domainCommonModule(testModules.domainCommonModule)
                .build()
    }
}
