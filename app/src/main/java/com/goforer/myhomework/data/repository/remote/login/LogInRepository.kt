package com.goforer.myhomework.data.repository.remote.login

import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.model.entity.login.LogInData
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogInRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<ResponseResult, ResponseResult>(false, viewModelScope) {
        override fun request() = restAPI.logIn(query.firstParam as LogInData)
    }.asSharedFlow
}
