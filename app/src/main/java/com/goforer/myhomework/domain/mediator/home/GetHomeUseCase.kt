package com.goforer.myhomework.domain.mediator.home

import com.goforer.myhomework.data.repository.remote.home.GetHomeRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHomeUseCase
@Inject
constructor(override val repository: GetHomeRepository) : RepoUseCase(repository)
