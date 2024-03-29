package com.packy.root.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.giftbox.navigation.giftBoxNavGraph
import com.example.home.root.HomeRootScreen
import com.example.home.root.HomeRoute.HOME_ROOT
import com.example.home.navigation.settingsNavGraph
import com.packy.core.page.navigation.commonNavGraph
import com.packy.createbox.navigation.createBoxNavGraph
import com.packy.domain.model.getbox.GiftBox
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
    moveToBoxDetail: (Long, Boolean) -> Unit,
    moveSettings: () -> Unit,
    closeCreateBox: () -> Unit,
    moveToShared: (Long) -> Unit,
    moveToBoxOpenMotion: (GiftBox) -> Unit,
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
            closeCreateBox = closeCreateBox,
            moveToHomeClear = moveToHomeClear
        )
        giftBoxNavGraph(
            navController = navController,
            closeGiftBox = moveToHomeClear,
            moveToShared = moveToShared
        )
        settingsNavGraph(
            navController = navController,
            logout = logout
        )
        commonNavGraph(
            navController = navController
        )
        composable(
            route = HOME_ROOT
        ) {
            HomeRootScreen(
                moveToCreateBox = moveToCreateBox,
                moveToBoxDetail = moveToBoxDetail,
                moveSettings = moveSettings,
                moveToBoxOpenMotion=  moveToBoxOpenMotion,
            )
        }
    }
}

object MainRoute {
    const val LAUNCH_NAV_GRAPH = "launchNavGraph"
    const val LAUNCH_ROUTE = "launchRoute"
    const val LAUNCH_ROUTE_OPEN_BOX = "launchRouteOpenBox"
    fun getLaunchOpenBox(boxId: String) = "$LAUNCH_ROUTE_OPEN_BOX/{$boxId}"
}