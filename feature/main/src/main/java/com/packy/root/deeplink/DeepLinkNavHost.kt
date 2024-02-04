package com.packy.root.deeplink

import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.packy.root.LaunchScreen
import com.packy.root.navigation.MainRoute

fun NavGraphBuilder.deepLinkNavGraph(
    navController: NavHostController,
) {

    composable(
        route = MainRoute.LAUNCH_ROUTE,
    ) {
        LaunchScreen(
            navController = navController,
            deepLinkController = DeepLinkController.NonDeepLink
        )
    }

    composable(
        route = MainRoute.LAUNCH_ROUTE_OPEN_BOX,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = DeepLinkRoute.GIFT_BOX_OPEN_LINK
                action = Intent.ACTION_VIEW
            }
        )
    ) { backStackEntry ->
        val boxId = backStackEntry.arguments?.getString("id")
        boxId?.let {
            val deepLinkController = DeepLinkController.OpenBox(it)
            LaunchScreen(
                navController = navController,
                deepLinkController = deepLinkController
            )
        } ?: LaunchScreen(
            navController = navController,
            deepLinkController = DeepLinkController.NonDeepLink
        )
    }
}