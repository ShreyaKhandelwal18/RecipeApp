package com.example.myreceipeapp

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@SuppressLint("SuspiciousIndentation")
@Composable
fun RecipeApp(navController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState
    NavHost(navController =navController , startDestination = Screen.RecipeScreen.route ){
     composable(route = Screen.RecipeScreen.route){
         RecipeScreen( viewState =viewState, navigateToDetail ={
             //this part is responsible for passing data from current screen ti detail screen
             navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
             navController.navigate(Screen.DetailsScreen.route)
         } )


     }

        composable(route=Screen.DetailsScreen.route){
          val category=navController.previousBackStackEntry?.savedStateHandle?.
          get<Category>("cat") ?:Category("","","","")
            CategoryDetailsScreen(category = category)
        }
    }

  }