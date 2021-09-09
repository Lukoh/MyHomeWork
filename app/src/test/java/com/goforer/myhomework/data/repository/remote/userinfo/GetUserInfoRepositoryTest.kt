package com.goforer.myhomework.data.repository.remote.userinfo

import com.goforer.myhomework.data.repository.remote.RepositoryTest
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.test.kit.QueryTool
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
class GetUserInfoRepositoryTest : RepositoryTest() {
    @Before
    fun setup() {
        setBaseRepository(
            GetUserInfoRepository(),
            QueryTool.getQuery(1, -1)
        )
    }

    @Test
    fun workTest() {
        runBlockingTest {
            coroutinesTestRule.managedCoroutineScope.launch {
                repository.doWork(this, Query().apply {
                    firstParam = 1
                    secondParam = -1
                }).test(this) {
                    this.assertValueAt(0, getResourceValue(userInfoTest.userInfo0))
                }
            }
        }
    }
}
