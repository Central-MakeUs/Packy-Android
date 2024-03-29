package com.packy.onboarding.onboarding

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() :
    MviViewModel<OnboardingIntent, OnboardingState, OnboardingEffect>() {

    override fun createInitialState(): OnboardingState = OnboardingState(
        currentPage = 0
    )

    override fun handleIntent() {
        subscribeIntent<OnboardingIntent.OnStartButtonClick> {
            sendEffect(OnboardingEffect.NavLoginScreenEffect)
        }
        subscribeIntent<OnboardingIntent.OnSkipButtonClick> {
            sendEffect(OnboardingEffect.NavLoginScreenEffect)
        }
        subscribeStateIntent<OnboardingIntent.OnNextButtonClick> { state, _ ->
            val currentPage = state.currentPage
            if (currentPage >= MAX_ONBOARDING_PAGE_SIZE) state
            else state.copy(currentPage = currentPage + 1)
        }
        subscribeStateIntent<OnboardingIntent.OnScrollPager> { state, intent ->
            state.copy(currentPage = intent.changePage)
        }
    }

    companion object {
        const val MAX_ONBOARDING_PAGE_SIZE = 1
    }
}