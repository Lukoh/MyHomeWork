package com.goforer.myhomework.presentation.ui.signup

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
import com.goforer.base.extension.addAfterTextChangedListener
import com.goforer.myhomework.R
import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.model.entity.signup.SignUpData
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentSignUpBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.signup.SignUpViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding
        get() = FragmentSignUpBinding::inflate

    private var nickname = ""
    private var introduction = ""
    private var password = ""

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModel.AssistedSignUpFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etNickname.addAfterTextChangedListener {
                nickname = it
                checkNext()
            }

            etNickname.setOnFocusChangeListener { _, hasFocus ->
                tvNicknameTitle.isSelected = hasFocus
            }

            etIntroduction.addAfterTextChangedListener {
                introduction = it
                checkNext()
            }

            etIntroduction.setOnFocusChangeListener { _, hasFocus ->
                tvIntroductionTitle.isSelected = hasFocus
            }

            etPassword.addAfterTextChangedListener {
                password = it
                checkNext()
            }

            etPassword.setOnFocusChangeListener { _, hasFocus ->
                tvPasswordTitle.isSelected = hasFocus
            }

            btNextBig.setOnClickListener {
                hideKeyboard()
                handleNext()
            }
        }
    }

    override fun onBackPressed() {
        setFragmentResult(
            FRAGMENT_REQUEST_FROM_BACKSTACK,
            bundleOf(FRAGMENT_RESULT_FROM_SIGN_UP to true)
        )
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun isFilled() =
        nickname.isNotEmpty() && introduction.isNotEmpty() && password.isNotEmpty()

    private fun checkNext() {
        binding.btNextBig.isEnabled = isFilled()
    }

    private fun handleNext() {
        hideKeyboard()
        if (isFilled()) {
            signUp(SignUpData(nickname, introduction, password))
        }
    }

    private fun signUp(signUpData: SignUpData) {
        val signUpViewModel: SignUpViewModel by viewModels {
            SignUpViewModel.provideFactory(
                signUpViewModelFactory,
                Params(Query().apply {
                    firstParam = signUpData
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            val responseResult = resource.getData() as? ResponseResult

                            responseResult?.let {
                                if (it.ok == "true") {
                                    localStorage.setSignedUp()
                                    onBackPressed()
                                } else {
                                    it.msg?.let { msg ->
                                        showErrorPopup(msg) {}
                                    }

                                    it.msg ?: showErrorPopup(getString(R.string.registered_user)) {}
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
}
