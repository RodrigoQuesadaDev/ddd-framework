package com.rodrigodev.common.exception;

import com.rodrigodev.common.function.ExceptionSupplier;
import com.rodrigodev.common.function.ThrowableMethodCallWithReturn;

/**
 * Created by Rodrigo Quesada on 12/04/15.
 */
public class ExceptionUtils {

    public static <T, E extends RuntimeException> T unchecked(
            ThrowableMethodCallWithReturn<T> methodCall, ExceptionSupplier<E> exceptionSupplier
    ) {
        try {
            return methodCall.call();
        }
        catch (Throwable e) {
            throw exceptionSupplier.get(e);
        }
    }

    public static <T> T unchecked(ThrowableMethodCallWithReturn<T> methodCall) {
        try {
            return methodCall.call();
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
