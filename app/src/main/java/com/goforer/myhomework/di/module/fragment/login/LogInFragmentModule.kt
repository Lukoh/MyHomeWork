package com.goforer.myhomework.di.module.fragment.login

import com.goforer.myhomework.presentation.ui.login.LogInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LogInFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeLogInFragment(): LogInFragment
}
