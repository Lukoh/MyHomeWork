package com.goforer.myhomework.presentation.vm.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.myhomework.domain.mediator.userinfo.GetUserInfoUseCase
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetUserInfoViewModel
@AssistedInject
constructor(
    useCase: GetUserInfoUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedUserInfoFactory {
        fun create(params: Params): GetUserInfoViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedUserInfoFactory, params: Params) = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
