package com.packy.core.designsystem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme

@Composable
fun PackyDialog(
    title: String,
    subTitle: String? = null,
    dismiss: String,
    confirm: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .width(294.dp)
                .background(
                    color = PackyTheme.color.white,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(height = 32.dp)
            Text(
                text = title,
                style = PackyTheme.typography.body02,
                color = PackyTheme.color.gray900
            )
            if (subTitle != null) {
                Spacer(height = 4.dp)
                Text(
                    text = subTitle,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.gray600
                )
            }
            Spacer(height = 32.dp)
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(),
                color = PackyTheme.color.gray300
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickableWithoutRipple {
                            onDismiss()
                        },
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = dismiss,
                        style = PackyTheme.typography.body04.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = PackyTheme.color.gray600
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = PackyTheme.color.gray300
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickableWithoutRipple {
                            onConfirm()
                        },
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = confirm,
                        style = PackyTheme.typography.body04.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = PackyTheme.color.gray900
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PackyDialogPreview() {
    PackyDialog(
        title = "Title",
        subTitle = "SubTitle",
        dismiss = "Dismiss",
        confirm = "Confirm",
        onConfirm = {

        },
    ) {

    }
}

@Composable
@Preview
fun PackyDialogNoSubTitlePreview() {
    PackyDialog(
        title = "Title",
        dismiss = "Dismiss",
        confirm = "Confirm",
        onConfirm = {

        },
    ) {

    }
}