package com.example.giftbox.boxdetailopen

import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.model.getbox.Envelope
import com.packy.domain.model.getbox.Gift
import com.packy.domain.model.getbox.GiftBox
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface GiftBoxDetailOpenIntent : MviIntent {
    data object OnPhotoClick: GiftBoxDetailOpenIntent

    data object OnLetterClick: GiftBoxDetailOpenIntent

    data object OnGiftClick: GiftBoxDetailOpenIntent

    data object OnBackClick : GiftBoxDetailOpenIntent
    data object OnCloseClick : GiftBoxDetailOpenIntent
}

data class GiftBoxDetailOpenState(
    val giftBox: GiftBox?,
    val youtubeState: YoutubeState = YoutubeState.INIT,
) : UiState

sealed interface GiftBoxDetailOpenEffect : SideEffect {
    data class ShowPhoto(
        val photoUrl: String?
    ) : GiftBoxDetailOpenEffect

    data class ShowLetter(
        val envelope: Envelope,
        val letterContent: String,
    ) : GiftBoxDetailOpenEffect

    data class ShowGift(
        val gift: Gift
    ) : GiftBoxDetailOpenEffect

    data object MoveToBack : GiftBoxDetailOpenEffect
    data object GiftBoxClose : GiftBoxDetailOpenEffect
}