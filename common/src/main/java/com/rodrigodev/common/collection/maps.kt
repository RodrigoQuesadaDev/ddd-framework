package com.rodrigodev.common.collection

import java.util.HashMap

/**
* Created by Rodrigo Quesada on 09/09/15.
*/
fun <K, V> Map<K, V>.toHashMap(): MutableMap<K, V> = HashMap(this)