package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.exceptions.NotFoundException
import com.bairei.springrecipes.services.RecipeService
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.nhaarman.mockito_kotlin.any
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model



/**
 * created by bairei on 21/09/17.
 */
class RecipeControllerTest {

    @Mock
    lateinit var recipeService: RecipeService

    lateinit var controller: RecipeController

    lateinit var mockMvc: MockMvc

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        controller = RecipeController(recipeService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ControllerExceptionHandler()).build()
    }

    @Test
    @Throws(Exception::class)
    fun testGetRecipe() {

        val recipe = Recipe()
        recipe.id = "1"

        `when`(recipeService.findById(anyString())).thenReturn(recipe)

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"))
    }

    @Test
    fun testGetRecipeNotFound(){
        `when`(recipeService.findById(anyString())).thenThrow(NotFoundException::class.java)

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound)
                .andExpect(view().name("404error"))
    }

    @Test
    @Throws(Exception::class)
    fun testGetNewRecipeForm() {
        val command = RecipeCommand()

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
    }

    @Test
    @Throws(Exception::class)
    fun testPostNewRecipeForm() {
        val command = RecipeCommand()
        command.id = "2"

        `when`(recipeService.saveRecipeCommand(any())).thenReturn(command)

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "0")
                .param("description", "some string")
                .param("directions", "some directions")
                .param("cookTime", "1")
                .param("prepTime", "1")
                .param("servings", "1")
        )
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/2/show"))
    }

    @Test
    @Throws(Exception::class)
    fun testPostNewRecipeFormValidationFail() {
        val command = RecipeCommand()
        command.id = "2"

        `when`(recipeService.saveRecipeCommand(any())).thenReturn(command)

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "0")
                .param("cookTime", "3000")

        )
                .andExpect(status().isOk)
                .andExpect(model().attributeExists("recipe")) //???
                .andExpect(view().name("recipe/recipeform"))
    }

    @Test
    @Throws(Exception::class)
    fun testGetUpdateView() {
        val command = RecipeCommand()
        command.id = "2"

        `when`(recipeService.findCommandById(anyString())).thenReturn(command)

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))
    }

    @Test
    fun testDeleteAction(){
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/"))
        verify(recipeService, times(1)).deleteById(anyString())
    }
}