package com.goforer.myhomework.di.module.fragment.home

import com.goforer.myhomework.presentation.ui.home.HomeFeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFeedFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFeedFragment(): HomeFeedFragment
}
