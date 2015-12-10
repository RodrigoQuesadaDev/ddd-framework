package com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
/*internal*/ class ReusedCommandException() : RuntimeException("Command has already been used.")