package com.example.dishcovery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dishcovery.ui.theme.DishcoveryTheme
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_NIGHT_NO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            DishcoveryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "terms"
                    ) {
                        composable("splash") { SplashScreen(navController) }
                        composable("terms") { TermsScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("search") {SearchScreen(navController)}
                        composable("saved") { SavedScreen(navController)  }
                        composable("create") {CreateScreen(navController)}
                    }
                }
            }
        }
    }
}

//@Preview(name = "Light Mode",uiMode = UI_MODE_NIGHT_NO)
//@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun TermsScreenPreview() {
//    DishcoveryTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            TermsScreen()
//        }
//    }
//}