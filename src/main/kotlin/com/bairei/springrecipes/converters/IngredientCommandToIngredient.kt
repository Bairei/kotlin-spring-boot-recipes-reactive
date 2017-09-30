package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.domain.Ingredient
import com.bairei.springrecipes.domain.Recipe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class IngredientCommandToIngredient @Autowired constructor(private val uomConverter : UnitOfMeasureCommandToUnitOfMeasure) : Converter<IngredientCommand, Ingredient> {

    @Nullable
    @Synchronized
    override fun convert(source: IngredientCommand?): Ingredient? {
        if (source == null) return null

        val ingredient = Ingredient()

        if(source.id != null) ingredient.id = source.id!!

        if(source.recipeId != null){
            val recipe = Recipe()
            recipe.id = source.recipeId
            recipe.addIngredient(ingredient)
        }

        ingredient.amount = source.amount
        ingredient.description = source.description
        ingredient.uom = uomConverter.convert(source.uom)!!
        return ingredient
    }
}