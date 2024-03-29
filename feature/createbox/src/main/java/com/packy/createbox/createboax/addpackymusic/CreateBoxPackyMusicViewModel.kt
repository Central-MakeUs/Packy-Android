package com.packy.createbox.createboax.addpackymusic

import androidx.lifecycle.viewModelScope
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.usecase.music.SuggestionMusicUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.map
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxPackyMusicViewModel @Inject constructor(
    private val suggestionMusicUseCase: SuggestionMusicUseCase
) :
    MviViewModel<CreateBoxPackyMusicIntent, CreateBoxPackyMusicState, CreateBoxPackyMusicEffect>() {

    override fun createInitialState(): CreateBoxPackyMusicState = CreateBoxPackyMusicState(
        currentMusicIndex = 0,
        music = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxPackyMusicIntent.OnBackClick> {
            sendEffect(CreateBoxPackyMusicEffect.MoveToBack)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxPackyMusicEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnSaveClick> {
            val youtubeMusic = currentState.getMusicUri()
            sendEffect(CreateBoxPackyMusicEffect.SaveMusic(youtubeMusic))
        }

        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusicState> { state, intent ->
            val newMusicList = state.music.toMutableList().apply {
                this[intent.index] = this[intent.index].copy(state = intent.state)
            }
            state.copy(music = newMusicList)
        }
        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusic> { state, intent ->
            val music = state.music.toMutableList().apply {
                this.replaceAll { music ->
                    music.copy(state = YoutubeState.PAUSED)
                }
            }
            state.copy(currentMusicIndex = intent.index, music = music)
        }
    }

    fun getSuggestionMusic() {
        viewModelScope.launch {
            suggestionMusicUseCase.suggestionMusic()
                .filterSuccess()
                .unwrapResource()
                .map { musics ->
                    musics.map { music ->
                        PackyMusic(
                            title = music.title,
                            hashTag = music.hashtags,
                            videoId = music.videoId,
                            state = YoutubeState.INIT,
                            youtubeMusicUri = music.youtubeUri
                        )
                    }
                }
                .collect { packyMusicList ->
                    setState {
                        it.copy(music = packyMusicList)
                    }
                }
        }
    }
}