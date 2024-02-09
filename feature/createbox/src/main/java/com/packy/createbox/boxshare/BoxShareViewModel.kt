package com.packy.createbox.boxshare

import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.createbox.boxtitle.BoxAddTitleIntent
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class BoxShareViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val boxDesignUseCase: GetBoxDesignUseCase,
) :
    MviViewModel<BoxShareIntent, BoxShareState, BoxShareEffect>() {
    override fun createInitialState(): BoxShareState = BoxShareState(
        boxImageUrl = null,
        boxTitle = null,
        shared = null
    )

    override fun handleIntent() {
        subscribeIntent<BoxShareIntent.ShareKakao>(createBox())
        subscribeIntent<BoxShareIntent.OnBackClick> { sendEffect(BoxShareEffect.MoveToBack) }
        subscribeIntent<BoxShareIntent.OnCloseClick> { sendEffect(BoxShareEffect.MoveToMain) }
    }

    private fun createBox(): suspend (BoxShareIntent.ShareKakao) -> Unit =
        {
            val createBox = createBoxUseCase.getCreatedBox()

            val box = createBoxUseCase.createBox(createBox)
            if (box is Resource.Success) {
                val boxDesign = boxDesignUseCase.getBoxDesignLocal().first()
                val kakaoCustomFeed = KakaoCustomFeed(
                    sender = createBox.senderName ?: "",
                    receiver = createBox.receiverName ?: "",
                    imageUrl = boxDesign?.boxNormal ?: "",
                    boxId = box.data.id
                )
                sendEffect(BoxShareEffect.KakaoShare(kakaoCustomFeed))
            }
        }

    suspend fun initState() {
        val boxImageUrl = boxDesignUseCase.getBoxDesignLocal().firstOrNull()?.boxNormal
        val boxTitle = createBoxUseCase.getCreatedBox().name
        setState(
            currentState.copy(
                boxImageUrl = boxImageUrl,
                boxTitle = boxTitle
            )
        )
    }

    fun kakaoShare(shared: Resource<String?>) {
        when (shared) {
            is Resource.Success -> {
                setState(currentState.copy(shared = true))
            }
            is Resource.NetworkError,
            is Resource.ApiError,
            is Resource.Loading,
            is Resource.NullResult -> {
                setState(currentState.copy(shared = false))
            }
        }
    }
}