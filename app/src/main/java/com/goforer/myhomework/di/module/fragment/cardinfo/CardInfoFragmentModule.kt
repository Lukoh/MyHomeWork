package com.goforer.myhomework.di.module.fragment.cardinfo

import com.goforer.myhomework.presentation.ui.cardinfo.CardInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CardInfoFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeCardInfoFragment(): CardInfoFragment
}
