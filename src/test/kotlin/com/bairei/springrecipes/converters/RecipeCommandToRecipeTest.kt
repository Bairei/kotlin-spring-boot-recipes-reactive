package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.CategoryCommand
import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.commands.NotesCommand
import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Difficulty
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RecipeCommandToRecipeTest {

    lateinit var converter: RecipeCommandToRecipe


    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = RecipeCommandToRecipe(CategoryCommandToCategory(),
                IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure()),
                NotesCommandToNotes())
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(RecipeCommand()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val recipeCommand = RecipeCommand()
        recipeCommand.id = RECIPE_ID
        recipeCommand.cookTime = COOK_TIME
        recipeCommand.prepTime = PREP_TIME
        recipeCommand.description = DESCRIPTION
        recipeCommand.difficulty = DIFFICULTY
        recipeCommand.directions = DIRECTIONS
        recipeCommand.servings = SERVINGS
        recipeCommand.source = SOURCE
        recipeCommand.url = URL

        val notes = NotesCommand()
        notes.id = NOTES_ID

        recipeCommand.notes = notes

        val category = CategoryCommand()
        category.id = CAT_ID_1

        val category2 = CategoryCommand()
        category2.id = CAT_ID2

        recipeCommand.categories.add(category)
        recipeCommand.categories.add(category2)

        val ingredient = IngredientCommand()
        ingredient.id = INGRED_ID_1

        val ingredient2 = IngredientCommand()
        ingredient2.id = INGRED_ID_2

        recipeCommand.ingredients.add(ingredient)
        recipeCommand.ingredients.add(ingredient2)

        //when
        val recipe = converter.convert(recipeCommand)

        assertNotNull(recipe)
        assertEquals(RECIPE_ID, recipe!!.id)
        assertEquals(COOK_TIME, recipe.cookTime)
        assertEquals(PREP_TIME, recipe.prepTime)
        assertEquals(DESCRIPTION, recipe.description)
        assertEquals(DIFFICULTY, recipe.difficulty)
        assertEquals(DIRECTIONS, recipe.directions)
        assertEquals(SERVINGS, recipe.servings)
        assertEquals(SOURCE, recipe.source)
        assertEquals(URL, recipe.url)
        assertEquals(NOTES_ID, recipe.notes.id)
        assertEquals(2, recipe.categories.size.toLong())
        assertEquals(2, recipe.ingredients.size.toLong())
    }

    companion object {
        val RECIPE_ID = "1"
        val COOK_TIME = Integer.valueOf("5")
        val PREP_TIME = Integer.valueOf("7")
        val DESCRIPTION = "My Recipe"
        val DIRECTIONS = "Directions"
        val DIFFICULTY = Difficulty.EASY
        val SERVINGS = Integer.valueOf("3")
        val SOURCE = "Source"
        val URL = "Some URL"
        val CAT_ID_1 = "1"
        val CAT_ID2 = "2"
        val INGRED_ID_1 = "3"
        val INGRED_ID_2 = "4"
        val NOTES_ID = "9"
    }

}