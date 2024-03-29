package com.packy.onboarding.onboarding

import com.packy.core.values.Strings
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface OnboardingIntent : MviIntent {
    data object OnStartButtonClick : OnboardingIntent
    data object OnNextButtonClick : OnboardingIntent
    data object OnSkipButtonClick : OnboardingIntent
    data class OnScrollPager(val changePage: Int) : OnboardingIntent
}

data class OnboardingState(
    val currentPage: Int,
) : UiState

sealed interface OnboardingEffect : SideEffect {
    data object NavLoginScreenEffect : OnboardingEffect
}
