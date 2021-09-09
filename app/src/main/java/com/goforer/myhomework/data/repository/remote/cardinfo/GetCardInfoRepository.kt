package com.goforer.myhomework.data.repository.remote.cardinfo

import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.source.model.entity.card.response.CardInfo
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCardInfoRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<CardInfo, CardInfo>(false, viewModelScope) {
        override fun request() = restAPI.getCardInfo(query.firstParam as Int)
    }.asSharedFlow
}
