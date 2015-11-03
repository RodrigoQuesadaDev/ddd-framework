package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin.CustomPluginManager
import java.net.URLClassLoader
import javax.inject.Inject

/**
* Created by Rodrigo Quesada on 08/09/15.
*/
internal class CustomDataNucleusClassLoader @Inject constructor(customPluginManager: CustomPluginManager) : URLClassLoader(customPluginManager.pluginUrls.toTypedArray())