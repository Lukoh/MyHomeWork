package com.goforer.myhomework.domain

import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.source.model.entity.BaseTest
import com.goforer.myhomework.data.source.network.response.Resource
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

open class UseCaseTest : TestWatcher() {
    lateinit var useCase: UseCase<Resource>

    lateinit var repository: Repository<Resource>

    lateinit var viewModel: MediatorViewModel

    var defaultQuery = Query()
    var defaultParams = Params(defaultQuery)

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> setBaseRepository(repoClass: Class<T>) {
        repository = Mockito.mock(repoClass as Class<Repository<Resource>>)
        Mockito.`when`(repository.doWork(isA<CoroutineScope>(), isA<Query>()))
            .thenReturn(
                flowOf(getResponseResult()).shareIn(
                    scope = GlobalScope,
                    started = SharingStarted.Eagerly,
                    replay = 1
                )
            )
    }

    fun getResponseResult(): Resource {
        return Resource().success(BaseTest().responseResult0)
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
