package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.converters.IngredientCommandToIngredient
import com.bairei.springrecipes.converters.IngredientToIngredientCommand
import com.bairei.springrecipes.converters.UnitOfMeasureCommandToUnitOfMeasure
import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.domain.Ingredient
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import com.bairei.springrecipes.repositories.reactive.UnitOfMeasureReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import reactor.core.publisher.Mono


class IngredientServiceImplTest {

    private val ingredientToIngredientCommand: IngredientToIngredientCommand
    private val ingredientCommandToIngredient: IngredientCommandToIngredient

    @Mock
    lateinit var recipeReactiveRepository: RecipeReactiveRepository

    @Mock
    lateinit var unitOfMeasureRepository: UnitOfMeasureReactiveRepository

    lateinit var ingredientService: IngredientService

    //init converters
    init {
        this.ingredientToIngredientCommand = IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand())
        this.ingredientCommandToIngredient = IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure())
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        ingredientService = IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeReactiveRepository = recipeReactiveRepository, unitOfMeasureRepository = unitOfMeasureRepository)
    }

    @Test
    @Throws(Exception::class)
    fun findByRecipeIdAndId() {
    }

    @Test
    @Throws(Exception::class)
    fun findByRecipeIdAndRecipeIdHappyPath() {
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

        `when`(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe))

        //then
        val ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block()

        //when
        assertEquals("3", ingredientCommand!!.id)
        verify(recipeReactiveRepository, times(1)).findById(anyString())
    }


    @Test
    @Throws(Exception::class)
    fun testSaveRecipeCommand() {
        //given
        val command = IngredientCommand()
        command.id = "3"
        command.recipeId = "2"
        command.uom = UnitOfMeasureCommand()
        command.uom.id = "1234"

        val savedRecipe = Recipe()
        savedRecipe.addIngredient(Ingredient())
        savedRecipe.ingredients.iterator().next().id = "3"

        `when`(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(Recipe()))
        `when`(recipeReactiveRepository.save(Mockito.any<Recipe>())).thenReturn(Mono.just(savedRecipe))

        //when
        val savedCommand = ingredientService.saveIngredientCommand(command).block()

        //then
        assertEquals("3", savedCommand!!.id)
        verify(recipeReactiveRepository, times(1)).findById(anyString())
        verify(recipeReactiveRepository, times(1)).save(Mockito.any<Recipe>())

    }

    @Test
    @Throws(Exception::class)
    fun testDeleteById() {
        //given
        val recipe = Recipe()
        val ingredient = Ingredient()
        ingredient.id = "3"
        recipe.addIngredient(ingredient)

        `when`(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe))
        `when`(recipeReactiveRepository.save(Mockito.any<Recipe>())).thenReturn(Mono.just(recipe))

        //when
        ingredientService.deleteById("1", "3")

        //then
        verify(recipeReactiveRepository, times(1)).findById(anyString())
        verify(recipeReactiveRepository, times(1)).save(Mockito.any<Recipe>())
    }
}