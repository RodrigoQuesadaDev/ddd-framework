package com.aticosoft.appointments.mobile.business


import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.querydsl.jdo.JDOQueryFactory
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Provider
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory

/**
 * Created by Rodrigo Quesada on 09/09/15.
 */
/*internal*/ class MainActivity : Activity() {

    private lateinit var textView: TextView
    @Inject protected lateinit var entityContext: Entity.Context
    @Inject protected lateinit var pmf: PersistenceManagerFactory

    override protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
        textView = findViewById(R.id.textView) as TextView
        (applicationContext as Application).applicationComponent.inject(this)
        renderStuff()
    }

    private fun renderStuff() {

        var appointment = Appointment(entityContext, 1, DateTime.now())

        val pm = pmf.persistenceManager
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
            if (tx.isActive) {
                tx.rollback()
            }
            pm.close()
        }

        val pm2 = pmf.persistenceManager
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
            textView.text = "$size | ${appointment.scheduledTime.toString()}"

            tx2.commit()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            if (tx2.isActive) {
                tx2.rollback()
            }
            pm2.close()
        }
    }
}