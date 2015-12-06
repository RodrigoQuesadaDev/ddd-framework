@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 08/12/15.
 */
abstract class CommandDelegate<V>(private val value: V) : ReadOnlyProperty<Command, V> {

    private var wasInitialized = false

    override fun getValue(thisRef: Command, property: KProperty<*>): V {
        if (!wasInitialized) {
            value.initUsing(thisRef)
            wasInitialized = true
        }
        return value
    }

    abstract fun V.initUsing(command: Command)
}

/***************************************************************************************************
 * Delegates
 **************************************************************************************************/

object CommandDelegates {

    private class NestedCommand<N : Command>(nestedCommand: N) : CommandDelegate<N>(nestedCommand) {

        override fun N.initUsing(command: Command) = initUsing(command)
    }

    fun <N : Command> N.delegate(): CommandDelegate<N> = NestedCommand(this)

    object Iterables {
        private class NestedCommands<I : Iterable<N>, N : Command>(iterable: I) : CommandDelegate<I>(iterable) {

            override fun I.initUsing(command: Command) = initIterableUsing(command)
        }

        fun <I : Iterable<N>, N : Command> I.delegate(): CommandDelegate<I> = NestedCommands(this)
    }

    object Maps {
        private class NestedCommandMap<M : Map<N1, N2>, N1 : Command, N2 : Command>(map: M) : CommandDelegate<M>(map) {

            override fun M.initUsing(command: Command) = forEach {
                it.key.initUsing(command)
                it.value.initUsing(command)
            }
        }

        fun <M : Map<N1, N2>, N1 : Command, N2 : Command> M.delegate(): CommandDelegate<M> = NestedCommandMap(this)

        object Values {
            private class NestedCommandMapValues<M : Map<K, N>, K, N : Command>(map: M) : CommandDelegate<M>(map) {

                override fun M.initUsing(command: Command) = values.initIterableUsing(command)
            }

            fun <M : Map<K, N>, K, N : Command> M.delegateValues(): CommandDelegate<M> = NestedCommandMapValues(this)
        }

        object Keys {
            private class NestedCommandMapKeys<M : Map<N, V>, N : Command, V>(map: M) : CommandDelegate<M>(map) {

                override fun M.initUsing(command: Command) = keys.initIterableUsing(command)
            }

            fun <M : Map<N, V>, N : Command, V> M.delegateKeys(): CommandDelegate<M> = NestedCommandMapKeys(this)
        }
    }
}

private inline fun <I : Iterable<N>, N : Command> I.initIterableUsing(command: Command) = forEach { it.initUsing(command) }