package com.packy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.packy.core.navigiation.appendArguments
import org.junit.Assert
import org.junit.Test

class AppendArgumentsTest {
    @Test
    fun `appendArguments required arguments Test`() {
        val route = "Test"
        val arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            },
        )

        val result = route.appendArguments(arguments)

        Assert.assertEquals(result, "Test/{id}")
    }

    @Test
    fun `appendArguments optional arguments Test`() {
        val route = "Test"
        val arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = 1
            },
        )
        val result = route.appendArguments(arguments)
        Assert.assertEquals(result, "Test?id={id}")
    }

    @Test
    fun `appendArguments optional with required arguments Test`() {
        val route = "Test"
        val arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = 1
            },
            navArgument("name") {
                type = NavType.StringType
            }
        )
        val result = route.appendArguments(arguments)
        Assert.assertEquals(result, "Test/{name}?id={id}")
    }
}