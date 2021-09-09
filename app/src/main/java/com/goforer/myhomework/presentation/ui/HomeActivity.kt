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

package com.goforer.myhomework.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.goforer.base.extension.gone
import com.goforer.base.extension.upShow
import com.goforer.base.utility.keyboard.KeyboardObserver
import com.goforer.myhomework.R
import com.goforer.myhomework.databinding.ActivityHomeBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class HomeActivity : AppCompatActivity(), HasAndroidInjector {
    internal lateinit var binding: ActivityHomeBinding

    internal var onKeyboardChange: ((status: Int) -> Unit) = {}

    private lateinit var keyboardObserver: KeyboardObserver

    private lateinit var glideRequestManager: RequestManager

    private var tabIndex: Int = -1

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        const val TAB_INDEX = "TAB_INDEX"
    }

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @GlideModule
        glideRequestManager = Glide.with(this)

        initKeyboardListener()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNavigationBar()
        binding.bottomNav.let { naviView ->
            supportFragmentManager.addOnBackStackChangedListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    if (naviView.isVisible)
                        naviView.gone()
                } else {
                    if (!naviView.isVisible)
                        naviView.upShow()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()

        return super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(TAB_INDEX, tabIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        tabIndex = savedInstanceState.getInt(TAB_INDEX, 0)
    }

    internal fun addKeyboardListener() {
        keyboardObserver.addListener { status ->
            onKeyboardChange(status)
        }
    }

    internal fun removeKeyboardListener() {
        keyboardObserver.removeListener()
    }

    internal fun hideKeyboard() {
        val inputManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        currentFocus?.windowToken?.let {
            inputManager.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun setUpBottomNavigationBar() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home_tab, R.id.setting))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initKeyboardListener() {
        keyboardObserver = KeyboardObserver(this)
    }
}
