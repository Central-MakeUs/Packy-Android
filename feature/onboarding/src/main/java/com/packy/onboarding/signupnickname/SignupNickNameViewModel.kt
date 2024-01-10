package com.packy.onboarding.signupnickname

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupNickNameViewModel @Inject constructor() :
    MviViewModel<SignupNickNameIntent, SignupNickNameState, SignupNickNameEffect>() {
    override fun createInitialState() = SignupNickNameState(
        inputNickName = null,
        isAvailableNickName = false
    )

    override fun handleIntent() {
        subscribeStateIntent<SignupNickNameIntent.OnChangeInputNickName> { state, intent ->
            state.copy(
                inputNickName = intent.inputNickName,
                isAvailableNickName = (state.inputNickName?.length ?: 0) >= 2
            )
        }
    }
}