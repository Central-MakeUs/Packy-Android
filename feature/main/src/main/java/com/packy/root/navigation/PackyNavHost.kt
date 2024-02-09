package com.packy.root.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.giftbox.navigation.giftBoxNavGraph
import com.example.home.navigation.homeNavGraph
import com.packy.core.page.navigation.commonNavGraph
import com.packy.createbox.navigation.createBoxNavGraph
import com.packy.onboarding.navigation.onboardingNavGraph
import com.packy.root.deeplink.deepLinkNavGraph

@Composable
fun PackyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    moveToHomeClear: () -> Unit,
    startDestination: String,
    kakaoLinkScheme: String,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    closeCreateBox: () -> Unit,
    logout: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {

        deepLinkNavGraph(
            navController = navController,
            kakaoLinkScheme
        )
        onboardingNavGraph(
            navController = navController,
            moveToHomeClear = moveToHomeClear
        )
        createBoxNavGraph(
            navController = navController,
            closeCreateBox = closeCreateBox
        )
        giftBoxNavGraph(
            navController = navController,
            closeGiftBox = moveToHomeClear
        )
        homeNavGraph(
            navController = navController,
            moveToCreateBox = moveToCreateBox,
            moveToBoxDetail = moveToBoxDetail,
            logout = logout
        )
        commonNavGraph(
            navController = navController
        )
    }
}

object MainRoute {
    const val LAUNCH_NAV_GRAPH = "launchNavGraph"
    const val LAUNCH_ROUTE = "launchRoute"
    const val LAUNCH_ROUTE_OPEN_BOX = "launchRouteOpenBox"
    fun getLaunchOpenBox(boxId: String) = "$LAUNCH_ROUTE_OPEN_BOX/{$boxId}"
}