package com.goforer.myhomework.presentation.vm.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.myhomework.domain.mediator.signup.SignUpUseCase
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SignUpViewModel
@AssistedInject
constructor(
    useCase: SignUpUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedSignUpFactory {
        fun create(params: Params): SignUpViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedSignUpFactory, params: Params) = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
