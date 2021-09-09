package com.goforer.myhomework.domain.mediator.home

import com.goforer.myhomework.data.repository.remote.home.GetFeedRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFeedUseCase
@Inject
constructor(override val repository: GetFeedRepository) : RepoUseCase(repository)
