package com.goforer.myhomework.domain

import androidx.paging.PagingData
import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.nhaarman.mockitokotlin2.isA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import org.junit.rules.TestWatcher
import org.mockito.Mockito

open class PagingDataUseCaseTest<P : Any> : TestWatcher() {
    lateinit var useCase: UseCase<PagingData<P>>
    lateinit var repository: Repository<PagingData<P>>
    lateinit var viewModel: MediatorViewModel
    var defaultQuery = Query()
    var defaultParams = Params(defaultQuery)

    @DelicateCoroutinesApi
    fun <T> setBaseRepository(repoClass: Class<T>) {
        @Suppress("UNCHECKED_CAST")
        repository = Mockito.mock(repoClass as Class<Repository<PagingData<P>>>)
        Mockito.`when`(repository.doWork(isA<CoroutineScope>(), isA<Query>()))
            .thenReturn(
                flowOf(PagingData.empty<P>()).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
            )
    }

    fun getParams(vararg params: Any?): Params {
        val query = Query()
        params.forEachIndexed { index, any ->
            when (index) {
                0 -> query.firstParam = any ?: 0
                1 -> query.secondParam = any ?: 0
                2 -> query.thirdParam = any
                3 -> query.forthParam = any
            }
        }

        return Params(query)
    }
}
