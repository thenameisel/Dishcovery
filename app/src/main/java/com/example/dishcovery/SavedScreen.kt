package com.example.dishcovery

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dishcovery.ui.theme.DishcoveryTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dishcovery.data.SavedRecipesViewModel


@Composable
fun SavedScreen(navController: NavController){
    val repository = (LocalContext.current.applicationContext as DishcoveryApp).repository

    // Initialize ViewModel with the existing repository
    val viewModel: SavedRecipesViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SavedRecipesViewModel(repository) as T
            }
        }
    )

    val searchQuery by viewModel.searchQuery
    val recipes = viewModel.filteredRecipes
    NavBarScreen(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged
            )
            Box (
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(8.dp),
                        clip = true
                    )
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                when {
                    // Loading state
                    recipes.isEmpty() && searchQuery.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {

                        }
                    }

                    // Empty state
                    recipes.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                            Text("No recipes found", style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    // Display recipes
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(recipes) { recipe ->
                                SearchItem(
                                    recipe = recipe,
                                    onItemClick = { id ->
                                        navController.navigate("view_recipe/$id")
                                    },
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SavedScreenPreview() {
    DishcoveryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val previewNavController = rememberNavControllerForPreview()
            SearchScreen(navController = previewNavController)
        }
    }
}

