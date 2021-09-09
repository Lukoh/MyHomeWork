package com.goforer.myhomework.data.repository.paging.source.home

import androidx.paging.PagingSource
import com.goforer.myhomework.data.repository.paging.source.BasePagingSourceTest
import com.goforer.myhomework.data.source.model.entity.home.HomeTest
import com.goforer.test.kit.QueryTool
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class FeedPagingSourceTest : BasePagingSourceTest() {
    @Test
    fun feedPagingSourceTest() {
        pagingFeedSource.setData(
            QueryTool.getQuery(1, 20),
            HomeTest().feed0
        )

        runBlockingTest {
            Assert.assertEquals(
                pagingFeedSource.load(PagingSource.LoadParams.Append(0, 20, true)),
                PagingSource.LoadResult.Page(
                    responseFeedTest.feed0.cards,
                    null,
                    1
                )
            )
        }
    }
}
