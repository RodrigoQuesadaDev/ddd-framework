package com.aticosoft.appointments.mobile.business.domain.specs

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
//TODO Check/change code on IdentityGenerator according to what is commented on corresponding forum thread? http://www.datanucleus.org/servlet/forum/viewthread_thread,7912

//TODO turn off logging (http://www.datanucleus.org/products/datanucleus/logging.html)

//TODO test DateTime-related conversion THOROUGHLY simulating different timezones: http://www.datanucleus.org/servlet/forum/viewthread_thread,7933

//TODO make QueryView entity-specific? That can prevent using a QueryView for different entity type by mistake. :)
//TODO this is not a priority since using a queryview for a different entity should  cause the required fields not to be detached and hence cause issues that should be noticed immediately.


/* Configuration */

//TODO test default values when first created (that is when the app is first used)

//TODO add validation for values (such as value range for max concurrent appointments)


/* Observables */

//TODO handle errors? Add Crashlytics for handling them (use RxJavaErrorHandler?)

//TODO take into account rollbacks during transactions??? (that would be for application services, though, because observation is now non-transactional)

//TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
//TODO for this probably a custom operator is necessary since RxJava retry*/CATCH*??? operators do not reset after a successful emission? (Use composition for this custom operator?)


/* Testing */

//TODO Currently package.jdo needs to redefine (and therefore duplicate) the metadata for the Entity class
