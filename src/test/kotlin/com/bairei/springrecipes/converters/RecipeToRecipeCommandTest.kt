package com.bairei.springrecipes.converters

import com.bairei.springrecipes.domain.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RecipeToRecipeCommandTest {


    lateinit var converter: RecipeToRecipeCommand

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = RecipeToRecipeCommand(
                CategoryToCategoryCommand(),
                IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand()),
                NotesToNotesCommand())
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(Recipe()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val recipe = Recipe()
        recipe.id = RECIPE_ID
        recipe.cookTime = COOK_TIME
        recipe.prepTime = PREP_TIME
        recipe.description = DESCRIPTION
        recipe.difficulty = DIFFICULTY
        recipe.directions = DIRECTIONS
        recipe.servings = SERVINGS
        recipe.source = SOURCE
        recipe.url = URL

        val notes = Notes()
        notes.id = NOTES_ID

        recipe.notes = notes

        val category = Category()
        category.id = CAT_ID_1

        val category2 = Category()
        category2.id = CAT_ID2

        recipe.categories.add(category)
        recipe.categories.add(category2)

        val ingredient = Ingredient()
        ingredient.id = INGRED_ID_1

        val ingredient2 = Ingredient()
        ingredient2.id = INGRED_ID_2

        recipe.ingredients.add(ingredient)
        recipe.ingredients.add(ingredient2)

        //when
        val command = converter.convert(recipe)

        //then
        assertNotNull(command)
        assertEquals(RECIPE_ID, command!!.id)
        assertEquals(COOK_TIME, command.cookTime)
        assertEquals(PREP_TIME, command.prepTime)
        assertEquals(DESCRIPTION, command.description)
        assertEquals(DIFFICULTY, command.difficulty)
        assertEquals(DIRECTIONS, command.directions)
        assertEquals(SERVINGS, command.servings)
        assertEquals(SOURCE, command.source)
        assertEquals(URL, command.url)
        assertEquals(NOTES_ID, command.notes.id)
        assertEquals(2, command.categories.size.toLong())
        assertEquals(2, command.ingredients.size.toLong())

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
