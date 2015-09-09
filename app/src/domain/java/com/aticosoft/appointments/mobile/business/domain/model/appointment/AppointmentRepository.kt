package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.querydsl.jdo.JDOQueryFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory

/**
 * Created by rodrigo on 05/09/15.
 */
@Singleton
class AppointmentRepository @Inject constructor(private val pmf: PersistenceManagerFactory) {

    public fun add(appointment: Appointment): Appointment {
        var result: Appointment
        val pm = pmf.getPersistenceManager()
        val tx = pm.currentTransaction()
        try {
            tx.begin()
            result = pm.makePersistent(appointment)
            tx.commit()
        }
        finally {
            if (tx.isActive()) {
                tx.rollback()
            }
            pm.close()
        }
        return result
    }

    public fun get(id: Long): Appointment {
        var appointment: Appointment
        var pm = pmf.getPersistenceManager()
        var queryFactory = JDOQueryFactory(object : Provider<PersistenceManager> {
            override fun get(): PersistenceManager {
                return pm
            }
        })

        var tx = pm.currentTransaction()
        try {
            tx.begin()

            var a = QAppointment.appointment
            appointment = queryFactory
                    .selectFrom(a)
                    .where(a.id.eq(id))
                    .fetchOne()

            tx.commit()
        }
        finally {
            if (tx.isActive()) {
                tx.rollback()
            }
            pm.close()
        }

        return appointment
    }
}