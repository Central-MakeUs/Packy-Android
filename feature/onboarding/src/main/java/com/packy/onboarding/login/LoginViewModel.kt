package com.packy.onboarding.login

import com.packy.common.authenticator.KakaoLoginController
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginController: KakaoLoginController
) : MviViewModel<LoginIntent, LoginState, LoginEffect>() {

    override fun createInitialState(): LoginState = LoginState(
        isLoading = false,
        loginState = LogindState.Waiting
    )

    override fun handleIntent() {
        subscribeIntent<LoginIntent.OnKakaoLoginButtonClick> {
            sendEffect(LoginEffect.LoginKakao)
        }
    }
}