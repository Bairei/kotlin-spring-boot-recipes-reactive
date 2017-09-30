package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.converters.IngredientCommandToIngredient
import com.bairei.springrecipes.converters.IngredientToIngredientCommand
import com.bairei.springrecipes.converters.UnitOfMeasureCommandToUnitOfMeasure
import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.domain.Ingredient
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.repositories.RecipeRepository
import com.bairei.springrecipes.repositories.UnitOfMeasureRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*


class IngredientServiceImplTest {

    @Mock
    lateinit var recipeRepository: RecipeRepository

    @Mock
    lateinit var unitOfMeasureRepository: UnitOfMeasureRepository

    @Mock
    lateinit var unitOfMeasureService: UnitOfMeasureService

    lateinit var ingredientService: IngredientService

    private val ingredientToIngredientCommand: IngredientToIngredientCommand

    private val ingredientCommandToIngredient : IngredientCommandToIngredient

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    //init converters
    init {
        this.ingredientToIngredientCommand = IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand())
        this.ingredientCommandToIngredient = IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure())
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        ingredientService = IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository, ingredientCommandToIngredient, unitOfMeasureRepository)
    }

    @Test
    @Throws(Exception::class)
    fun findByRecipeIdAndId() {
    }

    @Test
    @Throws(Exception::class)
    fun findByRecipeIdAndIngredientIdHappyPath() {
        //given
        val recipe = Recipe()
        recipe.id = "1"

        val ingredient1 = Ingredient()
        ingredient1.id = "1"

        val ingredient2 = Ingredient()
        ingredient2.id = "1"

        val ingredient3 = Ingredient()
        ingredient3.id = "3"

        recipe.addIngredient(ingredient1)
        recipe.addIngredient(ingredient2)
        recipe.addIngredient(ingredient3)
        val recipeOptional = Optional.of(recipe)

        `when`(recipeRepository.findById(anyString())).thenReturn(recipeOptional)

        //then
        val ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3")

        //when
        assertEquals("3", ingredientCommand.id)
        verify<RecipeRepository>(recipeRepository, times(1)).findById(anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testSaveRecipeCommand() {
        //given
        val command = IngredientCommand()
        command.id = "3"
        command.recipeId = "2"

        val recipeOptional = Optional.of(Recipe())

        val savedRecipe = Recipe()
        savedRecipe.addIngredient(Ingredient())
        savedRecipe.ingredients.iterator().next().id = "3"

        `when`(recipeRepository.findById(anyString())).thenReturn(recipeOptional)
        `when`<Recipe>(recipeRepository.save(com.nhaarman.mockito_kotlin.any())).thenReturn(savedRecipe)

        //when
        val savedCommand = ingredientService.saveIngredientCommand(command)

        //then
        assertEquals("3", savedCommand!!.id)
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, times(1)).save(any(Recipe::class.java))

    }

    @Test
    fun testDeleteById(){
        // given
        val recipe = Recipe()
        val ingredient = Ingredient()
        ingredient.id = "3"
        recipe.addIngredient(ingredient)
//        ingredient.recipe = recipe
        val recipeOptional = Optional.of(recipe)

        `when`(recipeRepository.findById(anyString())).thenReturn(recipeOptional)

        //when
        ingredientService.deleteIngredientFromRecipeById("1", "3")

        //then
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, times(1)).save(any(Recipe::class.java))
    }

}