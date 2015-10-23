package com.aticosoft.appointments.mobile.business.domain.model.common.queries;

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by rodrigo on 26/09/15.
 */
public interface ListQuery<E extends Entity> extends Query<List<E>> {

    @Override
    @NotNull
    List<E> execute();
}
