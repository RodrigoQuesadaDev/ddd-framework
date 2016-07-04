@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

import java.util.HashMap

/**
* Created by Rodrigo Quesada on 09/09/15.
*/
inline fun <K, V> Map<K, V>.toHashMap(): MutableMap<K, V> = HashMap(this)