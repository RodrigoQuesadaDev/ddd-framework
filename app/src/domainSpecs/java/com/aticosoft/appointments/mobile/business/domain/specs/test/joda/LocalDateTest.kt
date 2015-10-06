package com.aticosoft.appointments.mobile.business.domain.specs.test.joda

import org.assertj.core.api.Assertions.assertThat
import org.datanucleus.store.types.jodatime.converters.JodaLocalDateSqlDateConverter
import org.datanucleus.store.types.jodatime.converters.JodaLocalDateStringConverter
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.junit.Test

/**
 * Created by rodrigo on 05/10/15.
 */
internal class LocalDateTest {

    private object TimeZones {
        val JAPAN: String = "Asia/Tokyo"
        val COSTA_RICA: String = "America/Costa_Rica"
    }

    @Test
    fun testLocalDateStringConverter() {
        val expectedDate = LocalDate(2015, 10, 5)
        val converter = JodaLocalDateStringConverter()

        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.JAPAN))
        val stringDate = converter.toDatastoreType(expectedDate)
        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.COSTA_RICA))
        val actualDate = converter.toMemberType(stringDate)

        assertThat(actualDate).isEqualTo(expectedDate)
    }

    @Test
    fun testLocalDateSqlDateConverter() {
        val expectedDate = LocalDate(2015, 10, 5)
        val converter = JodaLocalDateSqlDateConverter()

        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.JAPAN))
        val sqlDate = converter.toDatastoreType(expectedDate)
        DateTimeZone.setDefault(DateTimeZone.forID(TimeZones.COSTA_RICA))
        val actualDate = converter.toMemberType(sqlDate)

        //TODO NUCJODATIME-23
        //assertThat(actualDate).isEqualTo(expectedDate)
        assertThat(actualDate).isNotEqualTo(expectedDate)
    }
}