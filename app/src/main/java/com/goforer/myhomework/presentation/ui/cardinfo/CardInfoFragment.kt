package com.goforer.myhomework.presentation.ui.cardinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.base.extension.loadPhotoUrl
import com.goforer.base.extension.setAspectRatio
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.card.response.CardInfo
import com.goforer.myhomework.data.source.model.entity.user.User
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentCardInfoBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.ui.cardinfo.adapter.RecommendCardAdapter
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.cardinfo.GetCardInfoViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CardInfoFragment : BaseFragment<FragmentCardInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCardInfoBinding
        get() = FragmentCardInfoBinding::inflate

    private val args: CardInfoFragmentArgs by navArgs()

    @Inject
    lateinit var getCardInfoViewModelFactory: GetCardInfoViewModel.AssistedCardInfoFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCardInfo(args.id)
    }

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_CARD_INFO to true)
        )
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun getCardInfo(id: Int) {
        val getCardInfoViewModel: GetCardInfoViewModel by viewModels {
            GetCardInfoViewModel.provideFactory(
                getCardInfoViewModelFactory,
                Params(Query().apply {
                    firstParam = id
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getCardInfoViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                @Suppress("UNCHECKED_CAST")
                                val userInfo = resource.getData() as? CardInfo

                                userInfo?.let {
                                    showUserInfo(userInfo.user)
                                    showCardInfo(userInfo.card)
                                    showRecommendCard(userInfo.recommend_cards as MutableList<Card>)
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

    private fun showUserInfo(user: User) {
        with(binding) {
            tvNickName.text = user.nickname
            tvIntroduction.text = user.introduction
            tvId.text = user.id.toString()
        }
    }

    private fun showCardInfo(card: Card) {
        with(binding) {
            tvDescription.text = card.description
            tvCardId.text = card.id.toString()
            ivPhoto.setAspectRatio(1, 1)
            ivPhoto.loadPhotoUrl(card.img_url)
        }
    }

    private fun showRecommendCard(cards: MutableList<Card>) {
        val recommendCardAdapter = RecommendCardAdapter { itemView, item ->
            itemView.findNavController().navigate(
                CardInfoFragmentDirections.actionCardInfoFragmentToCardInfoFragment(
                    item.id
                )
            )
        }

        with(binding) {
            rvRecommendCards.apply {
                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                recommendCardAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                itemAnimator?.changeDuration = 0
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                setHasFixedSize(true)
                recommendCardAdapter.setHasStableIds(true)
                setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
                layoutManager = linearLayoutManager
                adapter = recommendCardAdapter
                recommendCardAdapter.setCards(cards)
            }
        }
    }
}
