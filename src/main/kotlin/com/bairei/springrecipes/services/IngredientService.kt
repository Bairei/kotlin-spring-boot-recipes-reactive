package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand
import reactor.core.publisher.Mono

interface IngredientService {
    fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): Mono<IngredientCommand>
    fun saveIngredientCommand(command: IngredientCommand): Mono<IngredientCommand>
    fun deleteById(recipeId: String, idToDelete: String): Mono<Unit>
}