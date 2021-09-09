package com.goforer.myhomework.domain.mediator.userinfo

import com.goforer.myhomework.data.repository.remote.userinfo.GetUserInfoRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserInfoUseCase
@Inject
constructor(override val repository: GetUserInfoRepository) : RepoUseCase(repository)
