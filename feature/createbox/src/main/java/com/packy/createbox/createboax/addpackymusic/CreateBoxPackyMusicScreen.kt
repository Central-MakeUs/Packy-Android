package com.packy.createbox.createboax.addpackymusic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.indicator.PackyIndicator
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateBoxPackyMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxPackyMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        uiState.music.size
    })

    LaunchedEffect(viewModel) {
        viewModel.getSuggestionMusic()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxPackyMusicEffect.CloseBottomSheet -> closeBottomSheet()
                CreateBoxPackyMusicEffect.SaveMusic -> {
                    closeBottomSheet()
                    navController.popBackStack(
                        route = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC,
                        inclusive = true
                    )
                }

                CreateBoxPackyMusicEffect.MoveToBack -> navController.popBackStack()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(height = 12.dp)
        PackyTopBar.Builder()
            .startIconButton(icon = R.drawable.arrow_left) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnBackClick)
            }
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            BottomSheetTitle(
                BottomSheetTitleContent(
                    title = Strings.CREATE_BOX_ADD_PACKY_MUSIC_TITLE,
                    description = Strings.CREATE_BOX_ADD_PACKY_MUSIC_DESCRIPTION,
                )
            )
            Spacer(height = 100.dp)
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 24.dp),
                pageSpacing = 24.dp
            ) { index ->
                if (pagerState.currentPage != index) {
                    CreateBoxPackyMusicIntent.ChangeMusicState(
                        index,
                        YoutubeState.PAUSED
                    )
                }
                uiState.music.getOrNull(index)?.let { packMusic ->
                    packMusic.videoId?.let { videoId ->
                        YoutubePlayerFrom(
                            videoId = videoId,
                            packMusic = packMusic,
                            stateChange = viewModel::emitIntent,
                            index = index
                        )
                    }
                }
            }
            Spacer(32.dp)
            PackyIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 24.dp),
                pagerState = pagerState
            )
            Spacer(1f)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                style = buttonStyle.large.black, text = Strings.SAVE
            ) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun YoutubePlayerFrom(
    modifier: Modifier = Modifier,
    videoId: String,
    packMusic: PackyMusic,
    stateChange: emitMviIntent<CreateBoxPackyMusicIntent>,
    index: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        YoutubePlayer(
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .fillMaxWidth(),
            videoId = videoId,
            youtubeState = packMusic.state,
            stateListener = { state ->
                stateChange(
                    CreateBoxPackyMusicIntent.ChangeMusicState(
                        index,
                        state
                    )
                )
            },
        )
        Spacer(height = 16.dp)
        Text(
            text = packMusic.title,
            style = PackyTheme.typography.body01.copy(
                textAlign = TextAlign.Center
            ),
            color = PackyTheme.color.gray900
        )
        if(packMusic.hashTag.isNotEmpty()) {
            MusicHasTag(packMusic)
        }
        Spacer(height = 16.dp)
    }
}

@Composable
private fun MusicHasTag(packMusic: PackyMusic) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(packMusic.hashTag.size) {
            Text(
                text = packMusic.hashTag[it],
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.purple500
            )
            if (it != packMusic.hashTag.size - 1) {
                Spacer(4.dp)
            }
        }
    }
}
