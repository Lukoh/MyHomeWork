package com.goforer.myhomework.data.repository.remote.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.repository.paging.source.home.FeedPagingSource
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.home.response.Feed
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFeedRepository
@Inject
constructor(val pagingSource: FeedPagingSource) : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Card>, Feed>(false, viewModelScope) {
        override fun request() = restAPI.getFeed(1, NONE_ITEM_COUNT)

        override fun load(value: Feed, itemCount: Int) = Pager(
            config = PagingConfig(
                pageSize = itemCount,
                prefetchDistance = itemCount,
                initialLoadSize = itemCount
            )
        ) {
            pagingSource.setData(query, value)
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }.asSharedFlow
}
