package com.aticosoft.appointments.mobile.business;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by rodrigo on 21/08/15.
 */
@Accessors
public abstract class AbstractApplication<C extends ApplicationComponent> extends android.app.Application {

    @Getter protected C applicationComponent;

    @Override
    final public void onCreate() {
        super.onCreate();
        applicationComponent = createApplicationComponent();
    }

    protected abstract C createApplicationComponent();
}
