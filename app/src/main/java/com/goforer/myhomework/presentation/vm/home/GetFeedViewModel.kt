package com.goforer.myhomework.presentation.vm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goforer.myhomework.domain.mediator.home.GetFeedUseCase
import com.goforer.myhomework.presentation.vm.MediatorViewModel
import com.goforer.myhomework.presentation.vm.Params
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetFeedViewModel
@AssistedInject
constructor(
    useCase: GetFeedUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    @AssistedFactory
    interface AssistedFeedFactory {
        fun create(params: Params): GetFeedViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFeedFactory, params: Params) = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
