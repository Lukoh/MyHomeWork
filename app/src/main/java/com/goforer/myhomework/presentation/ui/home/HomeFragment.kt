package com.goforer.myhomework.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.home.response.Home
import com.goforer.myhomework.data.source.model.entity.user.User
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentHomeItemListBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.ui.home.adapter.PopularCardAdapter
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.home.GetHomeViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeItemListBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeItemListBinding
        get() = FragmentHomeItemListBinding::inflate

    private lateinit var homeTabFragment: HomeTabFragment

    @Inject
    lateinit var getHomeViewModelFactory: GetHomeViewModel.AssistedHomeFactory

    companion object {
        fun newInstance(tabFragment: HomeTabFragment) = HomeFragment().apply {
            homeTabFragment = tabFragment
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, HomeFragment::class.java.name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getHome()
    }

    override fun onBackPressed() {
        if (NavHostFragment.findNavController(this).popBackStack().not()) {
            //Last fragment: Do your operation here
            activity?.finishAndRemoveTask()
        }
    }

    private fun getHome() {
        val getHomeViewModel: GetHomeViewModel by viewModels {
            GetHomeViewModel.provideFactory(
                getHomeViewModelFactory,
                Params(Query().apply {
                    firstParam = -1
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getHomeViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                @Suppress("UNCHECKED_CAST")
                                val home = resource.getData() as? Home

                                home?.let {
                                    show(home)
                                }
                            }
                        }

                        Status.ERROR -> {
                            showErrorPopup(resource.getMessage()!!) {}

                        }

                        Status.LOADING -> {
                        }
                    }
                }
            }
        }
    }

    private fun show(home: Home) {
        setPopularUser(home.popular_users)
        setPopularCard(home.popular_cards)
    }

    private fun setPopularUser(userList: List<User>) {
        with(binding) {
            showUser(tvId1, tvNickname1, tvIntroduction1, userList[0])
            showUser(tvId2, tvNickname2, tvIntroduction2, userList[1])
            showUser(tvId3, tvNickname3, tvIntroduction3, userList[2])
            showUser(tvId4, tvNickname4, tvIntroduction4, userList[3])
            setOnClick(userList)
        }
    }

    private fun showUser(idView: TextView, nameView: TextView, introView: TextView, user: User) {
        idView.text = user.id.toString()
        nameView.text = user.nickname
        introView.text = user.introduction
    }

    private fun setPopularCard(cards: List<Card>) {
        showPopularCard(cards as MutableList<Card>)
    }

    private fun setOnClick(userList: List<User>) {
        with(binding) {
            vContents1.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToUserInfoFragment(
                        userList[0].id
                    )
                )
            }

            vContents2.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToUserInfoFragment(
                        userList[1].id
                    )
                )
            }

            vContents3.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToUserInfoFragment(
                        userList[2].id
                    )
                )
            }

            vContents4.setOnClickListener {
                it.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToUserInfoFragment(
                        userList[3].id
                    )
                )
            }
        }
    }

    private fun showPopularCard(cards: MutableList<Card>) {
        val popularCardAdapter = PopularCardAdapter { itemView, item ->
            itemView.findNavController().navigate(
                HomeTabFragmentDirections.actionHomeFragmentToCardInfoFragment(
                    item.id
                )
            )
        }

        with(binding) {
            rvPopularCards.apply {
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                popularCardAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                itemAnimator?.changeDuration = 0
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                setHasFixedSize(true)
                popularCardAdapter.setHasStableIds(true)
                setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
                layoutManager = linearLayoutManager
                adapter = popularCardAdapter
                popularCardAdapter.setCards(cards)
            }
        }
    }
}
