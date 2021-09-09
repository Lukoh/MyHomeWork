package com.goforer.myhomework.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.base.view.decoration.StaggeredGridItemOffsetDecoration
import com.goforer.myhomework.data.repository.paging.source.home.FeedPagingSource
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentItemListBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.ui.home.adapter.FeedAdapter
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.home.GetFeedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

class HomeFeedFragment : BaseFragment<FragmentItemListBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentItemListBinding
        get() = FragmentItemListBinding::inflate

    private lateinit var homeTabFragment: HomeTabFragment

    private var feedAdapter: FeedAdapter? = null

    @Inject
    lateinit var getFeedViewModelFactory: GetFeedViewModel.AssistedFeedFactory

    companion object {
        fun newInstance(tabFragment: HomeTabFragment) = HomeFeedFragment().apply {
            homeTabFragment = tabFragment
            arguments = Bundle(1).apply {
                putString(FRAGMENT_TAG, HomeFeedFragment::class.java.name)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedAdapter ?: getFeed()
        with(binding) {
            swipeRefreshLayoutContainer.layoutTransition.setAnimateParentHierarchy(false)
            swipeRefreshContainer.setOnRefreshListener {
                getFeed()
            }

            feedAdapter = feedAdapter ?: FeedAdapter { itemView, item ->
                itemView.findNavController().navigate(
                    HomeTabFragmentDirections.actionHomeFragmentToCardInfoFragment(
                        item.id
                    )
                )
            }

            rvList.apply {
                val gridManager = StaggeredGridLayoutManager(1, VERTICAL).apply {
                    gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                }

                adapter = feedAdapter
                feedAdapter?.stateRestorationPolicy = PREVENT_WHEN_EMPTY
                gridManager.spanCount = 1
                gridManager.orientation = resources.configuration.orientation
                itemAnimator?.changeDuration = 0
                addItemDecoration(StaggeredGridItemOffsetDecoration(0, 1), 0)
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
                layoutManager = gridManager
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            feedAdapter?.loadStateFlow?.collectLatest {
                var state: LoadState = LoadState.Loading

                when {
                    it.append is LoadState.Error -> state = it.append
                    it.refresh is LoadState.Error -> state = it.refresh
                    it.refresh is LoadState.NotLoading -> {
                        with(binding) {
                            if (feedAdapter?.itemCount == 0)
                                showNoPhotoMessage(rvList, noFeedContainer.root, true)
                            else
                                showNoPhotoMessage(rvList, noFeedContainer.root, false)
                        }
                    }
                }

                Timber.e("state.toString() $state")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        FeedPagingSource.nextPage = 1
    }

    override fun onDestroy() {
        super.onDestroyView()

        FeedPagingSource.nextPage = 1
    }

    override fun onBackPressed() {
        if (NavHostFragment.findNavController(this).popBackStack().not()) {
            //Last fragment: Do your operation here
            activity?.finishAndRemoveTask()
        }
    }

    private fun getFeed() {
        val getFeedViewModel: GetFeedViewModel by viewModels {
            GetFeedViewModel.provideFactory(
                getFeedViewModelFactory,
                Params(Query().apply {
                    firstParam = 1
                    secondParam = 20
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getFeedViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                binding.swipeRefreshContainer.isRefreshing = false
                                @Suppress("UNCHECKED_CAST")
                                val cards = resource.getData() as? PagingData<Card>

                                cards?.let {
                                    lifecycleScope.launchWhenCreated {
                                        feedAdapter?.submitData(it)
                                    }
                                }
                            }
                        }

                        Status.ERROR -> {
                            binding.swipeRefreshContainer.isRefreshing = false
                            showErrorPopup(resource.getMessage()!!) {}

                        }

                        Status.LOADING -> {
                            binding.swipeRefreshContainer.isRefreshing = true
                        }
                    }
                }
            }
        }
    }
}
