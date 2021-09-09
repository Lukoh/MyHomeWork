package com.goforer.myhomework.di.module.fragment.home

import com.goforer.myhomework.presentation.ui.home.HomeTabFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeTabFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeTabFragment(): HomeTabFragment
}
