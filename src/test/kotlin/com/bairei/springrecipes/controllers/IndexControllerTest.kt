package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.services.RecipeService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.ui.Model
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class IndexControllerTest {

    lateinit var indexController: IndexController

    @Mock
    lateinit var recipeService: RecipeService

    @Mock
    lateinit var model: Model

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        indexController = IndexController(recipeService)
    }

    @Test
    @Ignore
    fun testMockMVC() {
        val mockMVC : MockMvc = MockMvcBuilders.standaloneSetup(indexController).build()

        mockMVC.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("index"))
    }

    @Test
    @Ignore
    fun getIndexPage() {

        // given
        val recipes = HashSet<Recipe>()
        recipes.add(Recipe())

        val recipe : Recipe = Recipe()
        recipe.id = "4"
        recipes.add(recipe)

        `when`(recipeService.findAll()).thenReturn(Flux.fromIterable(recipes))

        val argumentCaptor = ArgumentCaptor.forClass(ArrayList::class.java)

        //when
        val viewName = indexController.getIndexPage(model)

        //then
        assertEquals(viewName, "index")
        verify(recipeService, times(1)).findAll()
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture())
        val recipeList = argumentCaptor.value
        assertEquals(2, recipeList.size)
    }

}
