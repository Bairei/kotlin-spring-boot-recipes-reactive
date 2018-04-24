package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.converters.RecipeCommandToRecipe
import com.bairei.springrecipes.converters.RecipeToRecipeCommand
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.exceptions.NotFoundException
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

class RecipeServiceImplTest {

    lateinit var recipeService: RecipeServiceImpl

    @Mock
    lateinit var recipeRepository: RecipeReactiveRepository

    @Mock
    lateinit var recipeToCommand : RecipeToRecipeCommand

    @Mock
    lateinit var recipeCommandToRecipe : RecipeCommandToRecipe

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        recipeService = RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToCommand)
    }

    @Test
    @Throws(Exception::class)
    fun getRecipesTest() {

        val recipe : Recipe = Recipe()
        val recipesData = HashSet<Recipe>()
        recipesData.add(recipe)

        `when`(recipeService.findAll()).thenReturn(Flux.just(recipe))

        val recipes = recipeService.findAll()?.collectList()?.block()

        assertEquals(recipes?.size, 1)
        verify(recipeRepository, times(1)).findAll()
    }

    @Test
    fun getRecipeByIdTest() {
        val recipe = Recipe()
        recipe.id = "1"

        `when`(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe))

        val recipeReturned = recipeService.findById("1").block()

        assertNotNull("Null recipe returned", recipeReturned)
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, never()).findAll()
    }

    @Test
    @Throws(Exception::class)
    fun getRecipeCommandByIdTest() {
        val recipe = Recipe()
        recipe.id = "1"

        `when`(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe))

        val recipeCommand = RecipeCommand()
        recipeCommand.id = "1"

        `when`(recipeToCommand.convert(any<Recipe>())).thenReturn(recipeCommand)

        val commandById = recipeService.findCommandById("1")

        assertNotNull("Null recipe returned", commandById)
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, never()).findAll()
    }

    @Test
    fun testDeleteById() {
        // given
        val idToDelete = "2"

        // when
        recipeService.deleteById(idToDelete)

        // no 'when', since method has void return type

        // then
        verify(recipeRepository, times(1)).deleteById(idToDelete)
    }

}
