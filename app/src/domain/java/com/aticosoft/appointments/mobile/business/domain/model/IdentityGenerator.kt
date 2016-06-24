package com.aticosoft.appointments.mobile.business.domain.model

import org.datanucleus.store.valuegenerator.AUIDGenerator
import org.datanucleus.store.valuegenerator.ValueGenerator
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/10/15.
 */
@Singleton
/*internal*/ class IdentityGenerator @Inject protected constructor() {

    private val valueGenerator: ValueGenerator<String> = AUIDGenerator("AUIDGenerator", Properties())

    fun generate(): String = valueGenerator.next()
}