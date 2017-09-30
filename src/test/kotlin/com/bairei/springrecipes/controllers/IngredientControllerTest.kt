package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.services.IngredientService
import com.bairei.springrecipes.services.RecipeService
import com.bairei.springrecipes.services.UnitOfMeasureService
import com.nhaarman.mockito_kotlin.any
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders


class IngredientControllerTest {

    @Mock
    lateinit var recipeService: RecipeService

    @Mock
    lateinit var ingredientService: IngredientService

    @Mock
    lateinit var unitOfMeasureService: UnitOfMeasureService

    lateinit var controller : IngredientController

    lateinit var mockMvc: MockMvc

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        controller = IngredientController(recipeService, ingredientService, unitOfMeasureService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testListIngredients() {
        // given
        val recipeCommand = RecipeCommand()
       `when`(recipeService.findCommandById(anyString())).thenReturn(recipeCommand)

        // when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))

        // then
        verify(recipeService, times(1)).findCommandById(anyString())

    }

    @Test
    fun testShowIngredient() {
        // given
        val ingredientCommand = IngredientCommand()

        // when
        `when`(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand)

        // then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"))
    }

    @Test
    fun testNewIngredientForm(){
        // given
        val recipeCommand = RecipeCommand()
        recipeCommand.id = "1"

        // when
        `when`(recipeService.findCommandById(anyString())).thenReturn(recipeCommand)
        `when`(unitOfMeasureService.listAllUoms()).thenReturn(emptyArray<UnitOfMeasureCommand>().toHashSet())

        // then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
        verify(recipeService, times(1)).findCommandById(anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateIngredientForm() {
        //given
        val ingredientCommand = IngredientCommand()

        //when
        `when`(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand)
        `when`(unitOfMeasureService.listAllUoms()).thenReturn(emptySet<UnitOfMeasureCommand>().toHashSet())

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
    }

    @Test
    fun testDeleteIngredient(){
        mockMvc.perform(get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/1/ingredients/"))

        verify(ingredientService, times(1)).deleteIngredientFromRecipeById(anyString(), anyString())
    }

    @Test
    @Throws(Exception::class)
    fun testSaveOrUpdate() {
        //given
        val command = IngredientCommand()
        command.id = "3"
        command.recipeId = "2"

        //when
        `when`(ingredientService.saveIngredientCommand(any())).thenReturn(command)

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "0")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"))

    }


}