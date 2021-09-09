package com.goforer.myhomework.di.module.fragment.userinfo

import com.goforer.myhomework.presentation.ui.userinfo.UserInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserInfoFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserInfoFragment(): UserInfoFragment
}
