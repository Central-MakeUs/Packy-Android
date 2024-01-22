package com.packy.createbox.createboax.addlatter

import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxLatterIntent : MviIntent {
    data object OnCloseClick : CreateBoxLatterIntent
    data object OnSaveClick : CreateBoxLatterIntent
    data class ChangeLatterText(val latter: String) : CreateBoxLatterIntent
    data class ChangeEnvelope(val envelopeId: Int) : CreateBoxLatterIntent
    data class GetEnvelope(val envelopeList: List<LatterEnvelopeItem>) : CreateBoxLatterIntent
}

data class LatterEnvelopeItem(
    val id: Int,
    val imageUri: String
)

data class CreateBoxLatterState(
    val latterText: String,
    val envelopeId: Int,
    val envelopeList: List<LatterEnvelopeItem>
) : UiState

sealed interface CreateBoxLatterEffect : SideEffect {
    data object CloseBottomSheet : CreateBoxLatterEffect
    data object SaveLatter : CreateBoxLatterEffect
    data object OverFlowLatterText : CreateBoxLatterEffect
}