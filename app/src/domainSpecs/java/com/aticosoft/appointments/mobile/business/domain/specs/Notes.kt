package com.aticosoft.appointments.mobile.business.domain.specs

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
//TODO test DateTime-related conversion THOROUGHLY simulating different timezones: http://www.datanucleus.org/servlet/forum/viewthread_thread,7933

//TODO turn off logging (http://www.datanucleus.org/products/datanucleus/logging.html)

//TODO make QueryView entity-specific? That can prevent using a QueryView for different entity type by mistake. :)
//TODO this is not a priority since using a queryview for a different entity should  cause the required fields not to be detached and hence cause issues that should be noticed immediately.


/* Configuration */

//TODO test default values when first created (that is when the app is first used)

//TODO add validation for values (such as value range for max concurrent appointments)


/* Observables */

//TODO handle errors? Add Crashlytics for handling them (use RxJavaErrorHandler?)

//TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
//TODO for this probably a custom operator is necessary since RxJava retry*/CATCH*??? operators do not reset after a successful emission? (Use composition for this custom operator?)


/* Testing */

//TODO Currently package.jdo needs to redefine (and therefore duplicate) the metadata for the Entity class

//TODO using package name "com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simple_action.test_data" produces weird DN error message: javax.jdo.JDOException: Class "Entity" : Cannot populate the class since it is already populated.
//TODO changing the "simple_action" part removes that error

/* Embedded Objects */
//TODO do dirty-checking on embedded objects
//TODO make sure embedded objects are not modified by services when passed from presentation layer
//TODO analyze/compare embedded objects & PersistableObject class and conclude what else should be similar (therefore some refactoring would be required?)

/* Events */
//TODO implement infrastructure feature to allow code to get executed each time the app is upgraded (events state should be reset)