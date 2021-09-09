package com.goforer.myhomework.presentation.ui.userinfo

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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.goforer.myhomework.data.source.model.entity.user.response.UserInfo
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentUserInfoBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.userinfo.GetUserInfoViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class UserInfoFragment : BaseFragment<FragmentUserInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserInfoBinding
        get() = FragmentUserInfoBinding::inflate

    private val args: UserInfoFragmentArgs by navArgs()

    @Inject
    lateinit var getUserInfoViewModelFactory: GetUserInfoViewModel.AssistedUserInfoFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserInfo(args.id)
    }

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_USER_INFO to true)
        )
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun getUserInfo(id: Int) {
        val getUserInfoViewModel: GetUserInfoViewModel by viewModels {
            GetUserInfoViewModel.provideFactory(
                getUserInfoViewModelFactory,
                Params(Query().apply {
                    firstParam = id
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getUserInfoViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                @Suppress("UNCHECKED_CAST")
                                val userInfo = resource.getData() as? UserInfo

                                userInfo?.let {
                                    show(userInfo)
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

    private fun show(userInfo: UserInfo) {
        with(binding) {
            tvNickName.text = userInfo.user.nickname
            tvIntroduction.text = userInfo.user.introduction
            tvUserId.text = userInfo.user.id.toString()
        }
    }
}
