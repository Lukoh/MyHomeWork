/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.myhomework.di.module.activity

import com.goforer.myhomework.di.module.fragment.cardinfo.CardInfoFragmentModule
import com.goforer.myhomework.di.module.fragment.home.HomeFeedFragmentModule
import com.goforer.myhomework.di.module.fragment.home.HomeFragmentModule
import com.goforer.myhomework.di.module.fragment.home.HomeTabFragmentModule
import com.goforer.myhomework.di.module.fragment.home.SettingFragmentModule
import com.goforer.myhomework.di.module.fragment.login.LogInFragmentModule
import com.goforer.myhomework.di.module.fragment.signup.SignUpFragmentModule
import com.goforer.myhomework.di.module.fragment.userinfo.UserInfoFragmentModule
import com.goforer.myhomework.presentation.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {
    @ContributesAndroidInjector(
        modules = [
            HomeTabFragmentModule::class,
            HomeFeedFragmentModule::class,
            HomeFragmentModule::class,
            SettingFragmentModule::class,
            UserInfoFragmentModule::class,
            CardInfoFragmentModule::class,
            SignUpFragmentModule::class,
            LogInFragmentModule::class
        ]
    )

    abstract fun contributeHomeActivity(): HomeActivity
}
