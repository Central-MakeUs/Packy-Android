package com.packy.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.giftbox.navigation.GiftBoxRoute
import com.example.home.bottomnavigation.HomeRoute.HOME_ROOT
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.deeplink.DeepLinkController
import com.packy.root.navigation.MainRoute
import kotlinx.coroutines.delay

@Composable
fun LaunchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    deepLinkController: DeepLinkController = DeepLinkController.NonDeepLink,
    viewModel: RootComposeViewModel = hiltViewModel()
) {

    LaunchedEffect(null) {
        val isUser = viewModel.checkUserStatusOnAppEntry() == UserState.REGISTERED
        if (!isUser) {
            navController.navigate(OnboardingRoute.ONBOARDING_NAV_GRAPH) {
                popUpTo(
                    MainRoute.LAUNCH_ROUTE
                ) {
                    inclusive = true
                }
            }
        } else {

            delay(1500)
            when (deepLinkController) {
                DeepLinkController.NonDeepLink -> navController.navigate(HOME_ROOT) {
                    popUpTo(
                        MainRoute.LAUNCH_ROUTE
                    ) {
                        inclusive = true
                    }
                }

                is DeepLinkController.OpenBox -> {
                    navController.navigate(GiftBoxRoute.GIFT_BOX_ROOT + "/${deepLinkController.boxId}" + "?" + "skipArr=false") {
                        popUpTo(
                            MainRoute.LAUNCH_ROUTE
                        ) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Scaffold { innerPadding ->
        val innerPadding = innerPadding
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = PackyTheme.color.purple500)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.launch_logo),
                contentDescription = "LOGO",
                tint = PackyTheme.color.white
            )
        }
    }
}