package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand

interface IngredientService {
    fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): IngredientCommand
    fun saveIngredientCommand(command: IngredientCommand): IngredientCommand?
    fun deleteIngredientFromRecipeById(recipeId: String, id: String)
}