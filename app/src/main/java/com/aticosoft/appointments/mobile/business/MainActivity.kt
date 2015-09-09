package com.aticosoft.appointments.mobile.business

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.querydsl.jdo.JDOQueryFactory
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Provider
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory


import kotlin.properties.Delegates

/**
 * Created by rodrigo on 09/09/15.
 */
class MainActivity : Activity() {

    private var textView: TextView by Delegates.notNull()
    private var pmf: PersistenceManagerFactory by Delegates.notNull()
        @Inject internal set

    override protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
        textView = findViewById(R.id.textView) as TextView
        (getApplicationContext() as Application).applicationComponent.inject(this)
        renderStuff()
    }

    private fun renderStuff() {

        var appointment = Appointment(DateTime.now())

        val pm = pmf.getPersistenceManager()
        val tx = pm.currentTransaction()
        try {
            tx.begin()

            pm.makePersistent(appointment)

            tx.commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            if (tx.isActive()) {
                tx.rollback()
            }
            pm.close()
        }

        val pm2 = pmf.getPersistenceManager()
        val queryFactory = JDOQueryFactory(object : Provider<PersistenceManager> {
            override fun get(): PersistenceManager {
                return pm2
            }
        })
        val tx2 = pm2.currentTransaction()
        try {
            tx2.begin()
            pm2.makePersistent(appointment)

            val a = QAppointment.appointment
            appointment = queryFactory
                    .selectFrom(a)
                    .where(a.id.eq(appointment.id))
                    .fetchOne()

            val size = queryFactory.selectFrom(a).fetchCount()
            textView.setText("$size | ${appointment.scheduledTime.toString()}")

            tx2.commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            if (tx2.isActive()) {
                tx2.rollback()
            }
            pm2.close()
        }
    }
}