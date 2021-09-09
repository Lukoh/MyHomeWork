package com.goforer.myhomework.data.source.network.resource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.testing.TestLifecycleOwner
import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.network.response.ApiResponse
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.test.kit.CoroutineTestRule
import com.goforer.test.kit.flow.test
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@DelicateCoroutinesApi
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class NetworkBoundWorkerTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun successCacheTest() {
        val resource =
            object : NetworkBoundWorker<ResponseResult, ResponseResult>(
                false, TestLifecycleOwner().lifecycleScope
            ) {
                override fun loadFromCache(
                    isLatest: Boolean,
                    itemCount: Int,
                    pages: Int
                ): Flow<ResponseResult> = flow {
                    emit(ResponseResult("OK"))
                }

                override fun request(): Flow<ApiResponse<ResponseResult>> = flow {
                    emit(ApiResponse.create(Response.success(ResponseResult("OK"))))
                }
            }

        runBlockingTest {
            coroutineTestRule.managedCoroutineScope.launch {
                resource.asSharedFlow.test(this) {
                    println(this.values())
                    assertValueAt(0, Resource().success(ResponseResult("OK")))
                }
            }
        }
    }

    @Test
    fun successRemoteTest() {
        val resource =
            object : NetworkBoundWorker<ResponseResult, ResponseResult>(
                false, TestLifecycleOwner().lifecycleScope
            ) {
                override fun request(): Flow<ApiResponse<ResponseResult>> = flow {
                    emit(ApiResponse.create(Response.success(ResponseResult("OK"))))
                }
            }

        runBlockingTest {
            coroutineTestRule.managedCoroutineScope.launch {
                resource.asSharedFlow.test(this) {
                    println(this.values())
                    assertValueAt(0, Resource().success(ResponseResult("OK")))
                }
            }
        }
    }

    @Test
    fun emptyResponseTest() {
        val resource =
            object : NetworkBoundWorker<ResponseResult, ResponseResult>(
                false, TestLifecycleOwner().lifecycleScope
            ) {
                override fun request(): Flow<ApiResponse<ResponseResult>> = flow {
                    emit(ApiResponse.create(Response.success(204, ResponseResult("OK"))))
                }
            }

        runBlockingTest {
            coroutineTestRule.managedCoroutineScope.launch {
                resource.asSharedFlow.test(this) {
                    println(this.values())
                    assertValueAt(0, Resource().success(""))
                }
            }
        }
    }

    @Test
    fun errorResponseTest() {
        val resource =
            object : NetworkBoundWorker<ResponseResult, ResponseResult>(
                false, TestLifecycleOwner().lifecycleScope
            ) {
                override fun request(): Flow<ApiResponse<ResponseResult>> = flow {
                    emit(ApiResponse.create(Throwable()))
                }
            }

        runBlockingTest {
            coroutineTestRule.managedCoroutineScope.launch {
                resource.asSharedFlow.test(this) {
                    println(this.values())
                    assertValueAt(0, Resource().error("unknown error", -1))
                }
            }
        }
    }

    @Test
    fun localResponseTest() {
        val resource =
            object : NetworkBoundWorker<ResponseResult, ResponseResult>(
                false, TestLifecycleOwner().lifecycleScope
            ) {
                override fun loadFromCache(
                    isLatest: Boolean,
                    itemCount: Int,
                    pages: Int
                ): Flow<ResponseResult> = flow {
                    emit(ResponseResult("OK"))
                }

                override fun request(): Flow<ApiResponse<ResponseResult>> = flow {
                    emit(ApiResponse.create(Throwable()))
                }
            }

        runBlockingTest {
            coroutineTestRule.managedCoroutineScope.launch {
                resource.asSharedFlow.test(this) {
                    println(this.values())
                    assertValueAt(0, Resource().success(ResponseResult("OK")))
                }
            }
        }
    }
}
