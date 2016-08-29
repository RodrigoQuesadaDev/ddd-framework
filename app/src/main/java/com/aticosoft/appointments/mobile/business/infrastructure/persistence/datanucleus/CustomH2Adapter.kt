package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus

import org.datanucleus.store.rdbms.adapter.H2Adapter
import java.sql.DatabaseMetaData

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */
internal class CustomH2Adapter(metadata: DatabaseMetaData) : H2Adapter(metadata) {

    init {
        supportedOptions.remove(CATALOGS_IN_TABLE_DEFINITIONS)
    }
}