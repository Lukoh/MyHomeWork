package com.goforer.myhomework.di.module.fragment.home

import com.goforer.myhomework.presentation.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}
