package com.packy.createbox.boxguide

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.packy.core.theme.PackyTheme
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val bottomSheetState = SheetState(skipHiddenState = false, skipPartiallyExpanded = false)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()

    LaunchedEffect(null) {
        scaffoldState.bottomSheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContent = {
            CreateBoxNavHost(
                modifier = Modifier.background(PackyTheme.color.white)
            ) {
                scope.launch {
                    scaffoldState.bottomSheetState.hide()
                }
            }
        }) { innerPadding ->
        Box(
            modifier = modifier
                .background(
                    PackyTheme.color.purple500
                )
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(

            ) {

            }
            if (scaffoldState.bottomSheetState.isVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            PackyTheme.color.black.copy(alpha = 0.6f)
                        )
                )
            }
        }

    }

}
