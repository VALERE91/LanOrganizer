package io.tech4fun.lanorganizer.ui.screens

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.tech4fun.lanorganizer.R
import io.tech4fun.lanorganizer.ui.viewmodels.LanViewModel

enum class AddLanRoutes(@StringRes val title: Int) {
    Start(R.string.create_lam),
    Details(R.string.lan_details),
    SelectGames(R.string.select_games),
    Summary(R.string.summary)
}

@Composable
fun AddLanScreen(navController: NavHostController,
                 modifier: Modifier = Modifier,
                 viewModel: LanViewModel = LanViewModel(),
                 onTitleChanged : (Int) -> Unit,
                 onCanNavigateBackChange: (Boolean) -> Unit){
    val backSackEntry by navController.currentBackStackEntryAsState()

    Scaffold() { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = AddLanRoutes.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = AddLanRoutes.Start.name) {
                onCanNavigateBackChange(false)
                StartCreateLanScreen(modifier, onNextButtonClicked = {
                    navController.navigate(AddLanRoutes.Details.name)
                })
            }
            composable(route = AddLanRoutes.Details.name) {
                onCanNavigateBackChange(true)
                EditLanScreen(modifier, uiState, onNextButtonClicked = {
                    navController.navigate(AddLanRoutes.SelectGames.name)
                })
            }
            composable(route = AddLanRoutes.SelectGames.name) {
                onCanNavigateBackChange(true)
                SelectGameScreen(modifier, uiState, onNextButtonClicked = {
                    navController.navigate(AddLanRoutes.Summary.name)
                })
            }
            composable(route = AddLanRoutes.Summary.name) {
                onCanNavigateBackChange(true)
                val context = LocalContext.current
                DetailsLanScreen(modifier, uiState, onNextButtonClicked = {
                    navController.popBackStack(AddLanRoutes.Start.name, inclusive = false)
                }, onShareButtonClicked = {
                    // Create an ACTION_SEND implicit intent with order details in the intent extras
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, uiState.name)
                        putExtra(Intent.EXTRA_TEXT, uiState.location)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            "Share your LAN"
                        )
                    )
                })
            }
        }
    }
}
