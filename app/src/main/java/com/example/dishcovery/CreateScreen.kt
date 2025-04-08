package com.example.dishcovery

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dishcovery.ui.theme.DishcoveryTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dishcovery.data.CreateRecipeViewModel
import com.example.dishcovery.data.CreateRecipeViewModelFactory
import com.example.dishcovery.data.repository.MealRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsInputSection(
    ingredients: MutableList<Pair<String, String>>,
    maxIngredients: Int = 20
) {
    var ingredientName by remember { mutableStateOf("") }
    var ingredientQuantity by remember { mutableStateOf("") }
    val showQuantityError = remember { mutableStateOf(false) }

    Column {
        // Display existing ingredients as chips
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ingredients.forEachIndexed { index, (name, quantity) ->
                IngredientChip(
                    name = name,
                    quantity = quantity,
                    onDelete = { ingredients.removeAt(index) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for new ingredient
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = ingredientName,
                onValueChange = { ingredientName = it },
                label = { Text("Ingredient") },
                modifier = Modifier.weight(2f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorTextColor = MaterialTheme.colorScheme.error
                )
            )

            OutlinedTextField(
                value = ingredientQuantity,
                onValueChange = { ingredientQuantity = it },
                label = { Text("Qty") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                isError = showQuantityError.value,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorTextColor = MaterialTheme.colorScheme.error
                )
            )
        }

        if (showQuantityError.value) {
            Text(
                text = "Cannot add quantity without ingredient",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add button
        Button(
            onClick = {
                if (ingredientName.isNotBlank()) {
                    ingredients.add(ingredientName to ingredientQuantity)
                    ingredientName = ""
                    ingredientQuantity = ""
                    showQuantityError.value = false
                } else if (ingredientQuantity.isNotBlank()) {
                    showQuantityError.value = true
                }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = ingredients.size < maxIngredients &&
                    (ingredientName.isNotBlank() || ingredientQuantity.isNotBlank())
        ) {
            Text("+ Add")
        }

        if (ingredients.size >= maxIngredients) {
            Text(
                text = "Maximum $maxIngredients ingredients reached",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun IngredientChip(name: String, quantity: String, onDelete: () -> Unit) {
    val displayText = if (quantity.isNotBlank()) "$name ($quantity)" else name

    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.tertiary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = displayText,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun CreateScreen(navController: NavController){

    val context = LocalContext.current
    val app = context.applicationContext as DishcoveryApp
    val viewModel: CreateRecipeViewModel = viewModel(
        factory = CreateRecipeViewModelFactory(app.repository)
    )
    val coroutineScope = rememberCoroutineScope()

    // Form state
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    val ingredients = remember { mutableStateListOf<Pair<String, String>>() }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val isFormValid = name.isNotBlank() &&
            category.isNotBlank() &&
            area.isNotBlank() &&
            ingredients.isNotEmpty() &&
            instructions.isNotBlank()


    //val json = Json { ignoreUnknownKeys = true }

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
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
                )
                    
                {
                    // Recipe Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Recipe Name *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = name.isBlank(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorBorderColor = MaterialTheme.colorScheme.onSecondary,
                            errorTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorLabelColor = MaterialTheme.colorScheme.onSecondary
                            )
                    )
                    if (name.isBlank()) {
                        Text(
                            text = "Required field",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Category
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = category.isBlank(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorBorderColor = MaterialTheme.colorScheme.onSecondary,
                            errorTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorLabelColor = MaterialTheme.colorScheme.onSecondary
                            )
                    )
                    if (category.isBlank()) {
                        Text(
                            text = "Required field",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Place of Origin
                    OutlinedTextField(
                        value = area,
                        onValueChange = { area = it },
                        label = { Text("Place of Origin *") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = area.isBlank(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorBorderColor = MaterialTheme.colorScheme.onSecondary,
                            errorTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorLabelColor = MaterialTheme.colorScheme.onSecondary
                            )
                    )
                    if (area.isBlank()) {
                        Text(
                            text = "Required field",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Ingredients Section
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    IngredientsInputSection(ingredients)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instructions
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = instructions,
                        onValueChange = { instructions = it },
                        label = { Text("Instructions *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 250.dp),
                        isError = instructions.isBlank(),
                        singleLine = false,
                        maxLines = Int.MAX_VALUE,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorBorderColor = MaterialTheme.colorScheme.onSecondary,
                            errorTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSecondary,
                            errorLabelColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                    if (instructions.isBlank()) {
                        Text(
                            text = "Required field",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (isFormValid) {
                                isLoading = true
                                errorMessage = null

                                coroutineScope.launch {
                                    try {
                                        viewModel.saveRecipe(
                                            name = name,
                                            category = category,
                                            area = area,
                                            ingredients = ingredients,
                                            instructions = instructions
                                        ).also { recipeId ->
                                            // Navigate back on success
                                            navController.popBackStack()
                                        }
                                    } catch (e: Exception) {
                                        errorMessage = "Failed to save: ${e.message}"
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            } else {
                                errorMessage = "Please fill all fields!"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading && isFormValid,
                        colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    ) {
                        Text("Save Recipe")
                    }

                }
            }
        }

    }

}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CreateScreenPreview() {
    DishcoveryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val previewNavController = rememberNavControllerForPreview()
            CreateScreen(navController = previewNavController)
        }
    }
}