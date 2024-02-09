package com.packy.createbox.boxtitle

import androidx.lifecycle.viewModelScope
import com.packy.createbox.common.name
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxAddTitleViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase
) :
    MviViewModel<BoxAddTitleIntent, BoxAddTitleState, BoxAddTitleEffect>() {
    override fun createInitialState(): BoxAddTitleState = BoxAddTitleState(
        boxTitle = ""
    )

    override fun handleIntent() {
        subscribeIntent<BoxAddTitleIntent.OnBackClick> {
            sendEffect(BoxAddTitleEffect.MoveToBack)
        }
        subscribeStateIntent<BoxAddTitleIntent.OnTitleChange> { state, intent ->
            createBoxUseCase.name(currentState.boxTitle)
            val boxAllReady = createBoxUseCase.getCreatedBox().boxAllReady()

            state.copy(
                boxTitle = intent.text,
                boxAllReady = boxAllReady
            )
        }
        subscribeIntent<BoxAddTitleIntent.MoveToNext> {
            createBoxUseCase.name(currentState.boxTitle)
            sendEffect(
                BoxAddTitleEffect.SaveBoxTitle(
                    boxId = createBoxUseCase.getCreatedBox().boxId ?: 0,
                    boxTitle = currentState.boxTitle
                )
            )
        }
    }

    fun initBoxTitle() {
        viewModelScope.launch {
            val createBox = createBoxUseCase.getCreatedBox()
            val name = createBox.name
            val boxAllReady = createBox.boxAllReady()

            setState(
                currentState.copy(
                    boxTitle = name ?: "",
                    boxAllReady = boxAllReady
                )
            )
        }
    }
}