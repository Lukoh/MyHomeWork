package com.goforer.myhomework.data.repository.remote.home

import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.source.model.entity.home.response.Home
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHomeRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<Home, Home>(false, viewModelScope) {
        override fun request() = restAPI.getHome()
    }.asSharedFlow
}
