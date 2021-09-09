package com.goforer.myhomework.presentation.vm.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.myhomework.domain.mediator.login.LogInUseCase
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LogInViewModel
@AssistedInject
constructor(
    useCase: LogInUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedLogInFactory {
        fun create(params: Params): LogInViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedLogInFactory, params: Params) = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
