package com.aticosoft.appointments.mobile.business;

/**
 * Created by rodrigo on 21/08/15.
 */
public abstract class AbstractApplication<C extends ApplicationComponent> extends android.app.Application {

    protected C applicationComponent;

    public C getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    final public void onCreate() {
        super.onCreate();
        applicationComponent = createApplicationComponent();
    }

    protected abstract C createApplicationComponent();
}
