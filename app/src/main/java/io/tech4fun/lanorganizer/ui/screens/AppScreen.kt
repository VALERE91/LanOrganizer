package io.tech4fun.lanorganizer.ui.screens

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.tech4fun.lanorganizer.R
import io.tech4fun.lanorganizer.ui.theme.LanOrganizerTheme
import io.tech4fun.lanorganizer.ui.viewmodels.LanViewModel

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon : ImageVector) {
    object Discover : Screen("discover", R.string.discover, Icons.Filled.Search)
    object LanList : Screen("lan_list", R.string.lan_list, Icons.Filled.List)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
    object Friends : Screen("friends", R.string.friends, Icons.Default.Face)
}

@Composable
fun TopBar(@StringRes title: Int,
                 canNavigateBack: Boolean,
                 modifier: Modifier = Modifier,
                 navigateBack: ()->Unit) {
    TopAppBar(
        title = { Text(stringResource(id = title)) },
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier, onNavigate: (Screen)->Unit) {
    val items = listOf(Screen.Discover,
                        Screen.LanList,
                        Screen.Profile,
                        Screen.Friends)

    BottomNavigation(modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    onNavigate(screen)
                }
            )
        }
    }
}

@Composable
fun AppScreen(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentScreenTitle by remember {
        mutableStateOf(Screen.Profile.resourceId)
    }

    var canNavigateBack by remember {
        mutableStateOf(false)
    }

    Scaffold(
    topBar = {
        TopBar(title = currentScreenTitle, canNavigateBack = canNavigateBack, modifier) {
            navController.navigateUp()
        }
    },
    bottomBar = {
        BottomBar(navController, modifier) {
            currentScreenTitle = it.resourceId
        }
    }) {
            innerPadding ->
        NavHost(navController, startDestination = Screen.Profile.route, Modifier.padding(innerPadding)) {
            createLanGraph(
                navController = navController,
                modifier,
                onTitleChanged = {
                    currentScreenTitle = it;
                },
                onCanNavigateBackChange = {
                    canNavigateBack = it
                }
            )
            composable(Screen.Friends.route) { FriendsList(navController) }
            composable(Screen.Discover.route) { Discover(navController) }
            composable(Screen.Profile.route) { Profile(navController) }
        }
    }
}