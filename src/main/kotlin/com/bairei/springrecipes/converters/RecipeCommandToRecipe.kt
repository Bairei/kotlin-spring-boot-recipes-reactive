package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Recipe
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class RecipeCommandToRecipe (private val categoryConverter: CategoryCommandToCategory,
                             private val ingredientConverter: IngredientCommandToIngredient,
                             private val notesConverter: NotesCommandToNotes) : Converter<RecipeCommand, Recipe> {


    @Synchronized
    @Nullable
    override fun convert(source: RecipeCommand?): Recipe? {
        if (source == null) return null

        val recipe = Recipe()
        recipe.id = source.id
        recipe.cookTime = source.cookTime
        recipe.prepTime = source.prepTime
        recipe.description = source.description
        recipe.difficulty = source.difficulty
        recipe.directions = source.directions
        recipe.servings = source.servings
        recipe.source = source.source
        recipe.url = source.url
        recipe.notes = notesConverter.convert(source.notes)!!

        if(source.categories.isNotEmpty()){
            source.categories
                    .forEach { category -> recipe.categories.add(categoryConverter.convert(category)!!) }
        }

        if(source.ingredients.isNotEmpty()){
            source.ingredients
                    .forEach { ingredient -> recipe.ingredients.add(ingredientConverter.convert(ingredient)!!) }
        }

        return recipe
    }
}