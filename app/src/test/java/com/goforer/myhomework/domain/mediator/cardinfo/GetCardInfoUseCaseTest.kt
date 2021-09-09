package com.goforer.myhomework.domain.mediator.cardinfo

import com.goforer.myhomework.data.repository.remote.cardinfo.GetCardInfoRepository
import com.goforer.myhomework.domain.UseCaseTest
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class GetCardInfoUseCaseTest : UseCaseTest() {
    @Before
    fun setup() {
        setBaseRepository(GetCardInfoRepository::class.java)
        useCase = GetCardInfoUseCase(repository as GetCardInfoRepository)
    }

    @Test
    fun executeTest() {
        runBlockingTest {
            useCase.run(this, defaultParams).test(this) {
                println(values())
                this.assertValueAt(0, getResponseResult())
            }
        }
    }
}
