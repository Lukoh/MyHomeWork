package com.goforer.myhomework.domain.mediator.login

import com.goforer.myhomework.data.repository.remote.login.LogInRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogInUseCase
@Inject
constructor(override val repository: LogInRepository) : RepoUseCase(repository)
