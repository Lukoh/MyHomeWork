package com.goforer.myhomework.domain.mediator.cardinfo

import com.goforer.myhomework.data.repository.remote.cardinfo.GetCardInfoRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCardInfoUseCase
@Inject
constructor(override val repository: GetCardInfoRepository) : RepoUseCase(repository)
