package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.common

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command

/**
 * Created by Rodrigo Quesada on 09/11/16.
 */
internal open class NestedCommand(val value: Int) : Command()