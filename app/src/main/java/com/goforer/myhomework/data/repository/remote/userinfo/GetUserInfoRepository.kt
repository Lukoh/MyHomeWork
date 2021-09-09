package com.goforer.myhomework.data.repository.remote.userinfo

import com.goforer.myhomework.data.repository.Repository
import com.goforer.myhomework.data.source.model.entity.user.response.UserInfo
import com.goforer.myhomework.data.source.network.response.Resource
import com.goforer.myhomework.data.source.network.worker.NetworkBoundWorker
import com.goforer.myhomework.presentation.vm.Query
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserInfoRepository
@Inject
constructor() : Repository<Resource>() {
    override fun doWork(viewModelScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<UserInfo, UserInfo>(false, viewModelScope) {
        override fun request() = restAPI.getUserInfo(query.firstParam as Int)
    }.asSharedFlow
}
