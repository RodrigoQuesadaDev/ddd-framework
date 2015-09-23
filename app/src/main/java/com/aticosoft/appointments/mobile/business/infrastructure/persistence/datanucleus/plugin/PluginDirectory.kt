package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import com.rodrigodev.common.file.pathOf

/**
 * Created by rodrigo on 08/09/15.
 */
internal class PluginDirectory(val path: String) {

    fun append(filePath: String) = pathOf(path, filePath)
}