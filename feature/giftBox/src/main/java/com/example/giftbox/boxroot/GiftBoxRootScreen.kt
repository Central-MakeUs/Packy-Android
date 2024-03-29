package com.example.giftbox.boxroot

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.theme.PackyTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun GiftBoxRootScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    skipArr: Boolean,
    shouldShowShared: Boolean,
    viewModel: GiftBoxRootViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxRootEffect.FailToGetGIftBox -> navController.navigate(
                    route = GiftBoxRoute.GIFT_BOX_ERROR,
                ) {
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }

                is GiftBoxRootEffect.GetGiftBox -> {
                    val route =
                        if (skipArr) GiftBoxRoute.getGiftBoxDetailOpenRoute(effect.giftBox, shouldShowShared)
                        else GiftBoxRoute.getGiftBoxArrRoute(effect.giftBox)
                    navController.navigate(route) {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        currentRoute?.let { popUpTo(it) { inclusive = true } }
                    }
                }
            }
        }
    }
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(color = PackyTheme.color.white)
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}