package com.aticosoft.appointments.mobile.business.domain.model.common.embedded

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.rodrigodev.common.properties.delegates.CachedProxyDelegate

/**
 * Created by Rodrigo Quesada on 06/07/16.
 */
internal open class EmbeddedDelegate<T, E : EmbeddedValueObject>(embeddedProperty: EmbeddedProperty<E>, delegator: Delegator<T, E>) : CachedProxyDelegate<T, E>(embeddedProperty, delegator) {

    class EmbeddedProperty<T>(get: () -> T, set: (T) -> Unit) : CachedProxyDelegate.ProxiedProperty<T>(get, set)

    open class Delegator<P, T>(proxyImpl: T.() -> P, unProxyImpl: P.() -> T) : CachedProxyDelegate.Proxier<P, T>(proxyImpl, unProxyImpl)
}

@ValueObject
internal interface EmbeddedValueObject