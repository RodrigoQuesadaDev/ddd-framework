package com.aticosoft.appointments.mobile.business.domain.model.common.queries;

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by rodrigo on 26/09/15.
 */
public interface UniqueQuery<E extends Entity> extends Query<E> {

    @Override
    @Nullable
    E execute();
}
