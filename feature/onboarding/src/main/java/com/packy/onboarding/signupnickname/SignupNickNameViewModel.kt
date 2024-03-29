package com.packy.onboarding.signupnickname

import androidx.lifecycle.SavedStateHandle
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.mvi.base.MviViewModel
import com.packy.onboarding.navigation.OnboardingRouteArgs.NICKNAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SignupNickNameViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<SignupNickNameIntent, SignupNickNameState, SignupNickNameEffect>() {
    override fun createInitialState() = SignupNickNameState(
        inputNickName = null,
        isAvailableNickName = false
    )

    override fun handleIntent() {
        subscribeStateIntent<SignupNickNameIntent.OnChangeInputNickName> { state, intent ->
            state.copy(
                inputNickName = intent.inputNickName,
                isAvailableNickName = (intent.inputNickName?.length ?: 0) >= 2
            )
        }
        subscribeIntent<SignupNickNameIntent.OnSaveButtonClick> {
            val signUp = signUpUseCase.getUserSignUpInfo().first()
            signUpUseCase.setUserSignUpInfo(
                signUp.copy(
                    nickname = currentState.inputNickName ?: ""
                )
            )
            sendEffect(SignupNickNameEffect.NavSignupProfileEffect)
        }
    }

    init {
        savedStateHandle.get<String>(NICKNAME)?.let {
            emitIntent(SignupNickNameIntent.OnChangeInputNickName(it))
        }
    }
}