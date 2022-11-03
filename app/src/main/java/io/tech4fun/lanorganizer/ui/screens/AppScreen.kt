package io.tech4fun.lanorganizer.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.tech4fun.lanorganizer.R
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon : ImageVector) {
    object Discover : Screen("discover", R.string.discover, Icons.Filled.Search)
    object LanList : Screen("lan_list", R.string.lan_list, Icons.Filled.List)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
    object Friends : Screen("friends", R.string.friends, Icons.Default.Face)
}

val MainScreens = listOf(Screen.Discover,
    Screen.LanList,
    Screen.Profile,
    Screen.Friends)

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
    BottomNavigation(modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        MainScreens.forEach { screen ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationDrawerContent(navController: NavController, modifier: Modifier = Modifier, onNavigate: (Screen)->Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        for (screen in MainScreens) {
            NavigationDrawerItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                label = {
                    Text(
                        text = stringResource(id = screen.resourceId),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = stringResource(id = screen.resourceId)
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                ),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(windowsSize: WindowSizeClass, modifier: Modifier = Modifier){
    val navController = rememberNavController()

    when(windowsSize.widthSizeClass){
        WindowWidthSizeClass.Compact -> {
            CompactWidthNavigation(modifier, navController = navController)
        }
        WindowWidthSizeClass.Medium -> {
            CompactWidthNavigation(navController = navController)
        }
        WindowWidthSizeClass.Expanded -> {
            ExpandedWidthNavigation(modifier, navController = navController)
        }
        else -> {
            CompactWidthNavigation(navController = navController)
        }
    }
}

@Composable
fun CompactWidthNavigation(modifier : Modifier = Modifier,
                           navController : NavHostController)
{
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
            innerPadding -> AppBody(innerPadding = innerPadding,
                                    navController = navController,
                                    modifier = modifier,
                                    onTitleChanged = {
                                        currentScreenTitle = it;
                                    },
                                    onCanNavigateBackChanged = {
                                        canNavigateBack = it
                                    })
    }
}

@Composable
fun MediumWidthNavigation()
{

}

@ExperimentalMaterial3Api
@Composable
fun ExpandedWidthNavigation(modifier : Modifier = Modifier,
                            navController : NavHostController)
{
    val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
    PermanentNavigationDrawer(
        modifier = Modifier.testTag(navigationDrawerContentDescription),
        drawerContent = {
            NavigationDrawerContent(modifier = modifier, navController = navController, onNavigate = {

            })
        }
    ) {
        AppBody(navController = navController,
                modifier = modifier,
                onTitleChanged = {

                },
                onCanNavigateBackChanged = {

                })
    }
}

@Composable
fun AppBody(modifier : Modifier = Modifier,
            innerPadding: PaddingValues = PaddingValues(Dp(10.0f)),
            navController : NavHostController,
            onTitleChanged : (Int) -> Unit,
            onCanNavigateBackChanged : (Boolean) -> Unit) {
    NavHost(navController, startDestination = Screen.Profile.route, Modifier.padding(innerPadding)) {
        createLanGraph(
            navController = navController,
            modifier,
            onTitleChanged = onTitleChanged,
            onCanNavigateBackChange = onCanNavigateBackChanged
        )
        composable(Screen.Friends.route) { FriendsList(navController) }
        composable(Screen.Discover.route) { Discover(navController) }
        composable(Screen.Profile.route) { Profile(navController) }
    }
}