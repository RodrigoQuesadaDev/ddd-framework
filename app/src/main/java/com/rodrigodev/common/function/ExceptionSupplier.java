package com.rodrigodev.common.function;

/**
 * Created by Rodrigo Quesada on 12/04/15.
 */
public interface ExceptionSupplier<E extends Exception> {

    E get(Throwable cause);
}
