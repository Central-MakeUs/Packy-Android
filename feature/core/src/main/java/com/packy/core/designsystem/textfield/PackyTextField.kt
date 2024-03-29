package com.packy.core.designsystem.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@Composable
fun PackyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    textAlign: TextAlign = TextAlign.Start,
    textFieldColor: Color = PackyTheme.color.gray100,
    placeholder: String? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    maxValues: Int = Int.MAX_VALUE,
    label: String? = null,
    keyboardOptions: KeyboardOptions = if(maxLines == 1)  KeyboardOptions(imeAction = ImeAction.Done) else KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showTrailingIcon: Boolean = false,
    trailingIconOnClick: (() -> Unit) = {
        onValueChange("")
    },
) {

    var focused by remember { mutableStateOf(false) }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = PackyTheme.color.gray900,
        backgroundColor = PackyTheme.color.gray400
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = modifier
                .onFocusChanged { focused = it.hasFocus }
                .focusRequester(focusRequester)
                .height(50.dp)
                .background(
                    color = textFieldColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
                .verticalScroll(rememberScrollState()),
            value = value,
            onValueChange = {
                onValueChange(it.take(maxValues))
            },
            textStyle = PackyTheme.typography.body04.copy(
                color = PackyTheme.color.gray900,
                textAlign = textAlign
            ),
            maxLines = maxLines,
            minLines = minLines,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Label(label)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = when (textAlign) {
                                TextAlign.Start -> Alignment.CenterStart
                                TextAlign.Center -> Alignment.Center
                                TextAlign.End -> Alignment.CenterEnd
                                else -> Alignment.CenterStart
                            }
                        ) {
                            Placeholder(
                                placeholder,
                                value,
                                textAlign,
                                focused
                            )
                            innerTextField()
                        }
                        if (showTrailingIcon && value.isNotEmpty() && focused) {
                            Spacer(modifier = Modifier.width(width = 8.dp))
                            CloseButton(trailingIconOnClick)
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun CloseButton(trailingIconOnClick: () -> Unit) {
    IconButton(
        onClick = trailingIconOnClick,
        modifier = Modifier
            .background(
                color = PackyTheme.color.gray400,
                shape = CircleShape
            )
            .size(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.cancle),
            contentDescription = "text field trailing icon",
            tint = PackyTheme.color.white
        )
    }
}

@Composable
private fun Placeholder(
    placeholder: String?,
    value: String,
    textAlign: TextAlign,
    focused: Boolean
) {
    if (placeholder != null && value.isEmpty() && !focused) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = placeholder,
            style = PackyTheme.typography.body04.copy(
                textAlign = textAlign
            ),
            color = PackyTheme.color.gray500,
        )
    }
}

@Composable
private fun Label(label: String?) {
    if (label != null) {
        Text(
            text = label,
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.gray800,
        )
        Spacer(modifier = Modifier.height(height = 4.dp))
    }
}
