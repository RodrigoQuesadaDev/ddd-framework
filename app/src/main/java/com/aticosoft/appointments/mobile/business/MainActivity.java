package com.aticosoft.appointments.mobile.business;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;
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

        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(appointment);

            appointment = pm.getObjectById(Appointment.class, appointment.id());
            int size = ((List) pm.newQuery(Appointment.class).execute()).size();
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
            pm.close();
        }
    }
}
