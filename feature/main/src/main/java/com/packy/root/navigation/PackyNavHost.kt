package com.packy.root.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.packy.createbox.navigation.createBoxNavGraph
import com.packy.onboarding.navigation.onboardingNavGraph

@Composable
fun PackyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    loggedIn: () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        onboardingNavGraph(navController, loggedIn)
        createBoxNavGraph(navController)
    }
}