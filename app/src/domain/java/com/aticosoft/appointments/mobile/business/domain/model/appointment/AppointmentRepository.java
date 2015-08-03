package com.aticosoft.appointments.mobile.business.domain.model.appointment;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
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
        PersistenceManager pm = persistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query query = pm.newQuery(Appointment.class, "id == " + id);
            query.setUnique(true);
            appointment = (Appointment) query.execute();
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
