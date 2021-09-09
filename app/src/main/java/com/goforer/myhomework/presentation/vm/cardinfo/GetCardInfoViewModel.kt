package com.goforer.myhomework.presentation.vm.cardinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.myhomework.domain.mediator.cardinfo.GetCardInfoUseCase
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetCardInfoViewModel
@AssistedInject
constructor(
    useCase: GetCardInfoUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedCardInfoFactory {
        fun create(params: Params): GetCardInfoViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedCardInfoFactory, params: Params) = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
