package com.rodrigodev.common.collection

import java.util.HashMap

/**
 * Created by rodrigo on 09/09/15.
 */
public fun <K, V> Map<K, V>.toHashMap(): MutableMap<K, V> = HashMap(this)