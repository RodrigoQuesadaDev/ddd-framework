package com.aticosoft.appointments.mobile.business.domain.model.appointment;

import com.querydsl.jdo.JDOQueryFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * Created by rodrigo on 27/07/15.
 */
@Singleton
public class AppointmentRepository {

    @Inject PersistenceManagerFactory pmf;

    @Inject
    public AppointmentRepository() {
    }

    private PersistenceManager persistenceManager() {
        return pmf.getPersistenceManager();
    }

    public Appointment add(Appointment appointment) {
        PersistenceManager pm = persistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            appointment = pm.makePersistent(appointment);
            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return appointment;
    }

    public Appointment get(long id) {
        Appointment appointment;
        final PersistenceManager pm = persistenceManager();
        JDOQueryFactory queryFactory = new JDOQueryFactory(new Provider<PersistenceManager>() {
            @Override public PersistenceManager get() {
                return pm;
            }
        });

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            QAppointment a = QAppointment.appointment;
            appointment = queryFactory
                    .selectFrom(a)
                    .where(a.id.eq(id))
                    .fetchOne();

            tx.commit();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return appointment;
    }
}
