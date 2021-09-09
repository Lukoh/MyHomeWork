package com.goforer.myhomework.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.goforer.base.extension.addAfterTextChangedListener
import com.goforer.myhomework.R
import com.goforer.myhomework.data.source.model.entity.ResponseResult
import com.goforer.myhomework.data.source.model.entity.login.LogInData
import com.goforer.myhomework.data.source.network.response.Status
import com.goforer.myhomework.databinding.FragmentLogInBinding
import com.goforer.myhomework.presentation.ui.BaseFragment
import com.goforer.myhomework.presentation.vm.Params
import com.goforer.myhomework.presentation.vm.Query
import com.goforer.myhomework.presentation.vm.login.LogInViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LogInFragment : BaseFragment<FragmentLogInBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLogInBinding
        get() = FragmentLogInBinding::inflate

    private var nickname = ""
    private var password = ""

    @Inject
    lateinit var logInViewModelFactory: LogInViewModel.AssistedLogInFactory

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
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun isFilled() =
        nickname.isNotEmpty() && password.isNotEmpty()

    private fun checkNext() {
        binding.btNextBig.isEnabled = isFilled()
    }

    private fun handleNext() {
        hideKeyboard()
        if (isFilled()) {
            logIn(LogInData(nickname, password))
        }
    }

    private fun logIn(logInData: LogInData) {
        val logInViewModel: LogInViewModel by viewModels {
            LogInViewModel.provideFactory(
                logInViewModelFactory,
                Params(Query().apply {
                    firstParam = logInData
                    secondParam = -1
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                logInViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            val responseResult = resource.getData() as? ResponseResult

                            responseResult?.let {
                                if (it.ok == "true") {
                                    localStorage.setLoggedIn()
                                    onBackPressed()
                                } else {
                                    it.msg?.let { msg ->
                                        showErrorPopup(msg) {}
                                    }

                                    it.msg ?: showErrorPopup(getString(R.string.no_user)) {}
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
