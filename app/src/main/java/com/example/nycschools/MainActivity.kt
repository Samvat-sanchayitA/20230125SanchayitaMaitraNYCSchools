package com.example.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschools.screen.SchoolInfoScreen
import com.example.nycschools.screen.SchoolListScreen
import com.example.nycschools.ui.theme.NYCSchoolsTheme
import com.example.nycschools.util.Screens
import com.example.nycschools.viewModel.SchoolListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, this@MainActivity)
            }
        }
    }
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun NavGraph(navController: NavHostController, activity: ComponentActivity) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    )
    {
        val viewModel by activity.viewModels<SchoolListViewModel>()
        composable(route = Screens.Home.route) {
            SchoolListScreen(viewModel, navController)
        }
        composable(route = Screens.Detail.route) {
            SchoolInfoScreen(viewModel,navController)
        }
    }
}
