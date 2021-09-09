package com.goforer.myhomework.domain.mediator.signup

import com.goforer.myhomework.data.repository.remote.signup.SignUpRepository
import com.goforer.myhomework.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase
@Inject
constructor(override val repository: SignUpRepository) : RepoUseCase(repository)
