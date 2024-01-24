package com.packy.createbox.boxguide

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor() :
    MviViewModel<BoxGuideIntent, BoxGuideState, BoxGuideEffect>() {
    override fun createInitialState(): BoxGuideState = BoxGuideState(
        photo = null,
        latter = null,
        youtubeUrl = null,
        sticker1 = null,
        sticker2 = null,
    )

    override fun handleIntent() {
        subscribeIntent<BoxGuideIntent.OnBackClick> { sendEffect(BoxGuideEffect.MoveToBack) }
        subscribeIntent<BoxGuideIntent.OnSaveClick> { sendEffect(BoxGuideEffect.SaveBox) }
        subscribeIntent<BoxGuideIntent.ShowBottomSheet> { sendEffect(BoxGuideEffect.ShowBottomSheet(it.boxGuideBottomSheetRoute)) }
        subscribeStateIntent<BoxGuideIntent.SavePhoto>(savePhoto())
        subscribeStateIntent<BoxGuideIntent.SaveLatter>(saveLatterBoxGuideStateSuspendFunction2())
    }

    private fun saveLatterBoxGuideStateSuspendFunction2(): suspend (BoxGuideState, BoxGuideIntent.SaveLatter) -> BoxGuideState =
        { state, intent ->
            state.copy(latter = intent.latter)
        }

    private fun savePhoto(): suspend (BoxGuideState, BoxGuideIntent.SavePhoto) -> BoxGuideState =
        { state, intent ->
            val photo = Photo(
                photoUrl = intent.imageUri,
                contentDescription = intent.contentDescription
            )
            state.copy(photo = photo)
        }
}