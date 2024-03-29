package com.packy.createbox.createboax.addLetter

import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxLetterIntent : MviIntent {
    data object OnCloseClick : CreateBoxLetterIntent
    data object OnSaveClick : CreateBoxLetterIntent
    data class ChangeLetterText(val Letter: String) : CreateBoxLetterIntent
    data class ChangeEnvelope(val envelopeId: Int) : CreateBoxLetterIntent
    data class GetEnvelope(val envelopeList: List<LetterEnvelope>) : CreateBoxLetterIntent
}

data class CreateBoxLetterState(
    val letterText: String,
    val envelopeId: Int,
    val previousLetterText: String,
    val previousEnvelopeId: Int,
    val envelopeList: List<LetterEnvelope>,
) : UiState {
    val changed: Boolean get() = letterText != previousLetterText || envelopeId != previousEnvelopeId
    fun getLetterEnvelope(): LetterEnvelope? = envelopeList.firstOrNull {
        it.id == envelopeId
    }
}

sealed interface CreateBoxLetterEffect : SideEffect {
    data class CloseBottomSheet(
        val changed: Boolean = false
    ) : CreateBoxLetterEffect

    data class SaveLetter(
        val envelopId: Int,
        val envelopUri: String,
        val LetterText: String,
    ) : CreateBoxLetterEffect

    data object OverFlowLetterText : CreateBoxLetterEffect
}