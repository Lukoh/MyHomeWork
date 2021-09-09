package com.goforer.myhomework.data.repository.paging.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goforer.myhomework.data.mock.MockRestAPI
import com.goforer.myhomework.data.repository.paging.source.home.FeedPagingSource
import com.goforer.myhomework.data.source.model.entity.home.HomeTest
import com.goforer.test.kit.CoroutineTestRule
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
abstract class BasePagingSourceTest : TestWatcher() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    internal val responseFeedTest = HomeTest.getInstance()
    
    @Inject
    internal lateinit var pagingFeedSource: FeedPagingSource


    @Inject
    lateinit var restAPI: MockRestAPI

    @Before
    fun setup() {
        pagingFeedSource = FeedPagingSource()
        restAPI = MockRestAPI()
        pagingFeedSource.restAPI = restAPI
    }
}
