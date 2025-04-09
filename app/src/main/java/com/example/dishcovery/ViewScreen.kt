package com.example.dishcovery

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dishcovery.ui.theme.DishcoveryTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dishcovery.data.RecipeDetailViewModel
import com.example.dishcovery.data.RecipeDetailViewModelFactory
import kotlinx.serialization.json.Json


//@Composable
//fun ViewChip(name: String) {
//    val displayText = name
//
//    Surface(
//        shape = RoundedCornerShape(16.dp),
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//        color = MaterialTheme.colorScheme.tertiary
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(end = 8.dp)
//        ) {
//            Text(
//                text = displayText,
//                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onPrimary,
//            )
//
//        }
//    }
//}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ViewScreen(navController: NavController,recipeId: String? = null){

    val context = LocalContext.current
    val repository = remember {
        (context.applicationContext as DishcoveryApp).repository
    }
    val apiService = remember {
        (context.applicationContext as DishcoveryApp).apiService
    }
    val viewModel: RecipeDetailViewModel = viewModel(
        factory = RecipeDetailViewModelFactory(repository, apiService)
    )

    LaunchedEffect(recipeId) {
        recipeId?.let { viewModel.loadRecipe(it) }
    }

    val recipe by viewModel.recipe

    val ingredients = remember(recipe) {
        recipe?.ingredients?.let {
            try {
                Json.decodeFromString<List<Pair<String, String>>>(it)
            } catch (e: Exception) {
                emptyList<Pair<String, String>>() // Fallback if parsing fails
            }
        } ?: emptyList()
    }

    NavBarScreen(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
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
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ){
                    recipe?.let { it1 ->
                        Text(
                            text = it1.name,
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Row {
                        Text(
                            text = "Category:",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        recipe?.let { it1 ->
                            Text(
                                text = it1.category,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = "Origin:",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        recipe?.let { it1 ->
                            Text(
                                text = it1.area,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ingredients:",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        )
                    ingredients.forEach {(name, quantity) ->
                        Row(modifier = Modifier.fillMaxWidth()){
                            Text(
                                modifier = Modifier.weight(1f),
                                text = name + ":",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )

                            Text(
                                text = quantity,
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )

                    recipe?.let { it1 ->
                        Text(
                            text = it1.instructions,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ViewScreenPreview() {
    DishcoveryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val previewNavController = rememberNavControllerForPreview()
            ViewScreen(navController = previewNavController)
        }
    }
}