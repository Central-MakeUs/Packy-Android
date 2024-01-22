package com.packy.createbox.createboax.addlatter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Constant
import com.packy.core.values.Strings
import com.packy.core.values.Strings.CREATE_BOX_ADD_LATTER_OVER_FLOW_LATTER_TEXT
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent

@Composable
fun CreateBoxLatterScreen(
    modifier: Modifier = Modifier,
    closeBottomSheet: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: CreateBoxLatterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.getLatterEnvelope()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxLatterEffect.CloseBottomSheet -> {
                    closeBottomSheet()
                }

                CreateBoxLatterEffect.SaveLatter -> {
                    closeBottomSheet()
                }

                CreateBoxLatterEffect.OverFlowLatterText -> {
                    showSnackbar(CREATE_BOX_ADD_LATTER_OVER_FLOW_LATTER_TEXT)
                }
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
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxLatterIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        BottomSheetTitle(
            BottomSheetTitleContent(
                title = Strings.CREATE_BOX_ADD_LATTER_TITLE,
                description = Strings.CREATE_BOX_ADD_LATTER_DESCRIPTION,
            )
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
        ) {

            Spacer(height = 32.dp)
            LatterForm(
                uiState.latterText,
                viewModel::emitIntent,
            )
            Spacer(height = 16.dp)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(uiState.envelopeList.size) { index ->
                    Envelope(
                        isSelected = uiState.envelopeId == uiState.envelopeList[index].id,
                        envelope = uiState.envelopeList[index],
                        onClick = viewModel::emitIntent
                    )
                }
            }
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                style = buttonStyle.large.black,
                text = Strings.SAVE,
                enabled = uiState.latterText.isNotEmpty()
            ) {
                viewModel.emitIntentThrottle(CreateBoxLatterIntent.OnSaveClick)
            }
            Spacer(16.dp)
        }
    }
}

@Composable
private fun LatterForm(
    text: String,
    onValueChange: emitMviIntent<CreateBoxLatterIntent>,
) {

    Box {
        PackyTextField(
            value = text,
            onValueChange = { onValueChange(CreateBoxLatterIntent.ChangeLatterText(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(
                    width = 4.dp,
                    color = PackyTheme.color.gray200,
                    shape = RoundedCornerShape(16.dp)
                ),
            textFieldColor = PackyTheme.color.gray100,
            placeholder = Strings.CREATE_BOX_ADD_LATTER_PLACEHOLDER,
            textAlign = TextAlign.Center,
            maxLines = Constant.MAX_LATTER_LINES,
        )
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp, end = 16.dp)
                .align(Alignment.BottomEnd),
            text = "${text.length}/${Constant.MAX_LATTER_TEXT}",
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.gray600,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Envelope(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    envelope: LatterEnvelopeItem,
    onClick: emitMviIntent<CreateBoxLatterIntent>,
) {
    val border = if (isSelected) {
        3.dp
    } else {
        0.dp
    }

    Surface(
        modifier = modifier
            .height(78.dp)
            .width(78.dp)
            .clickableWithoutRipple {
                onClick(CreateBoxLatterIntent.ChangeEnvelope(envelope.id))
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(border, PackyTheme.color.gray900),
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            model = envelope.imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}