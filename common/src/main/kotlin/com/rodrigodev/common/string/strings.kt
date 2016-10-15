package com.rodrigodev.common.string

import org.apache.commons.lang3.StringUtils

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
fun String.count(substring: String) = StringUtils.countMatches(this, substring)