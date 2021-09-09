package com.goforer.myhomework.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.goforer.base.extension.gone
import com.goforer.base.extension.show
import com.goforer.myhomework.R
import com.goforer.myhomework.databinding.FragmentHomeTabBinding
import com.goforer.myhomework.presentation.ui.BaseFragment


class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeTabBinding
        get() = FragmentHomeTabBinding::inflate

    private var currentTab = HOME

    private val fragments: List<BaseFragment<*>> = listOf(
        HomeFragment.newInstance(this),
        HomeFeedFragment.newInstance(this)
    )

    companion object {
        const val HOME = 0
        const val FEED = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(FRAGMENT_REQUEST_FROM_BACKSTACK) { _, bundle ->
            val isFromSignUp = bundle.getBoolean(FRAGMENT_RESULT_FROM_SIGN_UP)

            with(binding) {
                if (isFromSignUp) {
                    tvLogin.gone()
                    tvSignup.gone()
                    tvLogout.show()
                } else {
                    tvLogin.show()
                    tvSignup.show()
                    tvLogout.gone()
                }
            }
        }

        with(binding) {
            if (localStorage.isSignUp()) {
                tvLogin.show()
                tvLogout.show()
                tvSignup.gone()
            }
        }

        setAdapter()
        onTabChanged(binding.viewPager.currentItem)
        with(binding) {
            tvSignup.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToSignUpFragment()
                )
            }

            tvLogin.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToLogInFragment()
                )
            }

            tvLogout.setOnClickListener {
                localStorage.logOut()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (NavHostFragment.findNavController(this).popBackStack().not()) {
            //Last fragment: Do your operation here
            activity?.finishAndRemoveTask()
        }
    }

    private fun setAdapter() {
        with(binding) {
            with(viewPager) {
                adapter = object : FragmentStateAdapter(this@HomeTabFragment) {
                    private val mList: List<BaseFragment<*>> = fragments

                    override fun getItemCount(): Int {
                        return mList.size
                    }

                    override fun createFragment(position: Int): Fragment {
                        return mList[position]
                    }
                }

                registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        onTabChanged(position)
                        currentTab = position
                    }
                })

                tabItemHome.setOnClickListener {
                    currentItem = HOME
                }

                tabItemFeed.setOnClickListener {
                    currentItem = FEED
                }

                currentItem = currentTab
            }
        }
    }

    private fun onTabChanged(fragmentNum: Int) =
        with(binding) {
            setTabButtonUI(tabItemHome, fragmentNum == HOME)
            setTabButtonUI(tabItemFeed, fragmentNum == FEED)
        }

    private fun setTabButtonUI(view: TextView?, isSelected: Boolean) {
        view?.let {
            if (isSelected) {
                it.setTextColor(context.getColor(R.color.white))
                it.backgroundTintList =
                    ContextCompat.getColorStateList(homeActivity, R.color.colorPrimary)
            } else {
                it.setTextColor(context.getColor(R.color.colorPrimary))
                it.backgroundTintList =
                    ContextCompat.getColorStateList(homeActivity, R.color.colorTransparent)
            }
        }
    }
}
