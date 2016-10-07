package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import javax.jdo.annotations.EmbeddedOnly

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@ValueObject
@EmbeddedOnly
internal class RegisteredValueObject(var value: Int)

@ValueObject
@EmbeddedOnly
internal class NonRegisteredValueObject(var value: Int)