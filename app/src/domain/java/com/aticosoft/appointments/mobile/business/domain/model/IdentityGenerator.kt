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

    //TODO there shouldn't be DN code here because this is a class of the Domain
    private val valueGenerator: ValueGenerator<String> = AUIDGenerator("AUIDGenerator", Properties())

    fun generate(): String = valueGenerator.next()
}