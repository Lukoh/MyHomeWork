package com.goforer.myhomework.domain.mediator.signup

import com.goforer.myhomework.data.repository.remote.signup.SignUpRepository
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
class SignUpUseCaseTest : UseCaseTest() {
    @Before
    fun setup() {
        setBaseRepository(SignUpRepository::class.java)
        useCase = SignUpUseCase(repository as SignUpRepository)
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
