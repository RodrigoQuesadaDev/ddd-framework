package com.aticosoft.appointments.mobile.business;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment;
import com.querydsl.jdo.JDOQueryFactory;

import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * Created by rodrigo on 15/08/15.
 */
public class MainActivity extends Activity {

    private TextView textView;
    @Inject PersistenceManagerFactory pmf;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        textView = (TextView) findViewById(R.id.textView);
        ((Application) getApplicationContext()).applicationComponent().inject(this);
        renderStuff();
    }

    private void renderStuff() {

        Appointment appointment = new Appointment(DateTime.now());

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            pm.makePersistent(appointment);

            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        final PersistenceManager pm2 = pmf.getPersistenceManager();
        JDOQueryFactory queryFactory = new JDOQueryFactory(new Provider<PersistenceManager>() {
            @Override public PersistenceManager get() {
                return pm2;
            }
        });
        tx = pm2.currentTransaction();
        try {
            tx.begin();
            pm2.makePersistent(appointment);

            QAppointment a = QAppointment.appointment;
            appointment = queryFactory
                    .selectFrom(a)
                    .where(a.id.eq(appointment.id()))
                    .fetchOne();

            long size = queryFactory.selectFrom(a).fetchCount();
            textView.setText(size + " | " + appointment.scheduledTime().toString());

            tx.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm2.close();
        }
    }
}
