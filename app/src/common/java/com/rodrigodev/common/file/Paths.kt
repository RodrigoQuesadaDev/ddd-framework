package com.rodrigodev.common.file

import org.apache.commons.io.FilenameUtils
import java.io.File

/**
 * Created by rodrigo on 08/09/15.
 */

fun pathOf(vararg parts: String) = parts
        .map { FilenameUtils.normalizeNoEndSeparator(it) }
        .joinToString(File.separator)