package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationCommand
import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationService
import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 25/10/15.
 */
@Singleton
internal class TestDataServices @Inject constructor(
        val insertData: InsertData,
        val deleteData: DeleteData,
        val changeData: ChangeData
) {

    @Singleton
    class InsertData @Inject constructor(
            services: ApplicationService.Services,
            private val identityGenerator: IdentityGenerator,
            private val testDataRepository: TestDataRepository
    ) : ApplicationService<InsertData.Command>(services) {

        class Command(val value: Int) : ApplicationCommand

        override fun doExecute(command: Command) {
            with(command) {
                testDataRepository.add(TestData(identityGenerator.generate(), value))
            }
        }
    }

    @Singleton
    class DeleteData @Inject constructor(
            services: ApplicationService.Services,
            private val testDataRepository: TestDataRepository,
            private val testDataQueries: TestDataQueries
    ) : ApplicationService<DeleteData.Command>(services) {

        class Command(val value: Int) : ApplicationCommand

        override fun doExecute(command: Command) {
            with(command) {
                testDataQueries.valueIs(value).execute()?.let { data ->
                    testDataRepository.remove(data)
                }
            }
        }
    }

    @Singleton
    class ChangeData @Inject constructor(
            services: ApplicationService.Services,
            private val testDataQueries: TestDataQueries
    ) : ApplicationService<ChangeData.Command>(services) {

        class Command(val currentValue: Int, val targetValue: Int) : ApplicationCommand

        override fun doExecute(command: Command) {
            with(command) {
                testDataQueries.valueIs(currentValue).execute()?.let { data ->
                    data.value = targetValue
                }
            }
        }
    }
}