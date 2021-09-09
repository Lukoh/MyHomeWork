package com.goforer.myhomework.di.module.fragment.signup

import com.goforer.myhomework.presentation.ui.signup.SignUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignUpFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment
}
