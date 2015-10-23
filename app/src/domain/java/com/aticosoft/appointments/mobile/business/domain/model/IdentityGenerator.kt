package com.aticosoft.appointments.mobile.business.domain.model

import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.store.connection.ManagedConnection
import org.datanucleus.store.rdbms.valuegenerator.TableGenerator
import org.datanucleus.store.valuegenerator.ValueGenerationConnectionProvider
import org.datanucleus.store.valuegenerator.ValueGenerationManager
import org.datanucleus.store.valuegenerator.ValueGenerator
import java.sql.Connection
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.PersistenceManagerFactory

/**
 * Created by rodrigo on 04/10/15.
 */
@Singleton
/*internal*/ class IdentityGenerator @Inject constructor(
        private val pmf: PersistenceManagerFactory
) {

    private val valueGenerator: ValueGenerator<Long>

    init {
        val pm = pmf.persistenceManager as JDOPersistenceManager
        val storeManager = pm.executionContext.storeManager
        val mgr = ValueGenerationManager()

        val properties = Properties()
        properties.setProperty("sequence-name", "GLOBAL")

        //TODO report issue below?
        @Suppress("UNCHECKED_CAST")
        val valueGenerator = mgr.createValueGenerator("MyGenerator",
                TableGenerator::class.java, properties, storeManager,
                object : ValueGenerationConnectionProvider {

                    var connection: ManagedConnection? = null

                    override fun retrieveConnection(): ManagedConnection? {
                        connection = storeManager.getConnection(Connection.TRANSACTION_NONE)
                        return connection
                    }

                    override fun releaseConnection() {
                        if (connection != null) {
                            //TODO report issue below?
                            connection?.close()
                            connection = null
                        }
                    }
                }) as ValueGenerator<Long>
        this.valueGenerator = valueGenerator
    }

    fun generate(): Long = valueGenerator.next()
}