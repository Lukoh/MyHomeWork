package com.goforer.myhomework.data.repository.remote.home

import androidx.paging.PagingData
import com.goforer.myhomework.data.repository.paging.source.home.FeedPagingSource
import com.goforer.myhomework.data.repository.remote.RepositoryTest
import com.goforer.myhomework.data.source.model.entity.card.Card
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
class GetFeedRepositoryTest : RepositoryTest() {
    @Before
    fun setup() {
        setBaseRepository(
            GetFeedRepository(FeedPagingSource()),
            QueryTool.getQuery(1, 20)
        )
    }

    @Test
    fun workTest() {
        runBlockingTest {
            coroutinesTestRule.managedCoroutineScope.launch {
                repository.doWork(this, Query().apply {
                    firstParam = 1
                    secondParam = 20
                }).test(this) {
                    this.assertValue {
                        @Suppress("UNCHECKED_CAST")
                        (it.getData() as? PagingData<Card>) is PagingData<Card>
                    }
                }
            }
        }
    }
}
