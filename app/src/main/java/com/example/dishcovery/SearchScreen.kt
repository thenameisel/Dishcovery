package com.example.dishcovery

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import com.example.dishcovery.ui.theme.DishcoveryTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.dishcovery.data.SavedRecipesViewModel
import com.example.dishcovery.data.SearchViewModel
import com.example.dishcovery.data.local.entities.MealEntity

@Composable
fun SearchItem(
    recipe: MealEntity,
    onItemClick: (String) -> Unit,
    navController: NavController? = null,
    modifier: Modifier = Modifier

){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .clickable { onItemClick(recipe.id.toString()) }
            .padding(8.dp)

    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(recipe.mealThumb!=""){
                AsyncImage(
                    model = recipe.mealThumb,
                    contentDescription = recipe.name,
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.mipmap.ic_launcher_foreground),
                    error = painterResource(R.mipmap.ic_launcher_foreground)
                )

            } else {
                Image(
                    painter = painterResource(R.mipmap.ic_launcher_foreground),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Column (modifier = Modifier.weight(1f)){
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.height(24.dp))
                if(recipe.area != "Unknown") {
                    Text(
                        text = recipe.area,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }

        }
    }
}



@Composable
fun SearchScreen(navController: NavController){
    val context = LocalContext.current
    val viewModel: SearchViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val app = context.applicationContext as DishcoveryApp
                return SearchViewModel(
                    repository = app.repository,
                    apiServiceMeal = app.apiService
                ) as T
            }
        }
    )

    val recipes by viewModel.recipes
    val searchQuery by viewModel.searchQuery
    val isLoading by viewModel.isLoading
    viewModel.searchRecipes()
    NavBarScreen(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    viewModel.onSearchQueryChanged(query)
                    if (query.length > 2) {
                        viewModel.searchRecipes(query)
                        Log.d("API_DEBUG", "NOT EMPTY search")
                    } else if (query.isEmpty()) {
                        viewModel.searchRecipes()
                        Log.d("API_DEBUG", "EMPTY search")
                    }
                }
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
            ){
                if (recipes.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        text = "None found.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    ) // Should show if API returns null
                } else {
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
                                    navController.navigate("view_recipe/${recipe.id}")
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



@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
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

