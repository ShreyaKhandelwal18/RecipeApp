package com.example.myreceipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//view model ensures the communication between data and ui
class MainViewModel: ViewModel(){

private  val _categoriesState= mutableStateOf(RecipeState())
    //we want to change the state of user interface when ever we change the recipe state
val categoriesState : State<RecipeState> =_categoriesState


init {
    fetchCategories()
}

    private fun fetchCategories(){
       //suspend function is the function which only  runs in background , not in the block the main thread
        //if u want to start a suspend function u actually have to started the coroutine
        viewModelScope.launch {
            try {
                val response= recipeService.getCategories()
                _categoriesState.value= _categoriesState.value.copy(
                    list=response.categories,
                    loading = false,
                    error = null
                )

            }catch (e:Exception){
              _categoriesState.value=_categoriesState.value.copy(
                  loading = false,
                  error = "Error Fetching Categories ${e.message}"
              )
            }
        }

    }

    data class RecipeState(
        val loading:Boolean=true,
        val list: List<Category> = emptyList(),
        //if we do not have error it is empty
        val error: String? = null
    )

}