package com.example.dishcovery

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import com.example.dishcovery.ui.theme.DishcoveryTheme

@Composable
fun HomeMenuButton(
    title: Int,
    contents: Int,
    destination: String,
    navController: NavController? = null,
    modifier: Modifier = Modifier
){

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true
            )
            .clickable { navController?.navigate(destination) }
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.weight(1f)){
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = contents),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .padding(start = 16.dp),
                contentScale = ContentScale.Fit
                )
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
    NavBarScreen(navController = navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                HomeMenuButton(
                    title = R.string.homeSearchTitle,
                    contents = R.string.homeSearchContents,
                    destination = "Search",
                    navController = navController
                )

                Spacer(modifier = Modifier.height(20.dp))

                HomeMenuButton(
                    title = R.string.homeSavedTitle,
                    contents = R.string.homeSavedContents,
                    destination = "Search",
                    navController = navController
                )

                Spacer(modifier = Modifier.height(20.dp))

                HomeMenuButton(
                    title = R.string.homeCreateTitle,
                    contents = R.string.homeCreateContents,
                    destination = "Search",
                    navController = navController
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    DishcoveryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val previewNavController = rememberNavControllerForPreview()
            HomeScreen(navController = previewNavController)
        }
    }
}

// Add this helper function to your project
@Composable
fun rememberNavControllerForPreview(): NavController {
    val context = LocalContext.current
    return remember {
        NavController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
    }
}
