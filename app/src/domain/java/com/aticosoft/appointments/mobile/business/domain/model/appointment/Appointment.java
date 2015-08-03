package com.aticosoft.appointments.mobile.business.domain.model.appointment;

import org.joda.time.DateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by rodrigo on 26/07/15.
 */
@Data
@EqualsAndHashCode(exclude = "id")
@Getter(AccessLevel.NONE)
@Accessors
public class Appointment {

    @Getter long id;
    @Getter DateTime scheduledTime;

    public Appointment(DateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
