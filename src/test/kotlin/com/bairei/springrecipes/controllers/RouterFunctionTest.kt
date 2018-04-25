package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.config.WebConfig
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.services.RecipeService
import org.bson.types.Binary
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

class RouterFunctionTest {

    lateinit var webTestClient: WebTestClient

    @Mock
    lateinit var recipeService: RecipeService

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val webConfig = WebConfig()

        val routerFunction = webConfig.routes(recipeService)

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build()
    }

    @Test
    @Throws(Exception::class)
    fun testGetRecipes() {
        `when`(recipeService.findAll()).thenReturn(Flux.just())

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
    }

    @Test
    @Throws(Exception::class)
    fun testGetRecipesWithData() {
        `when`(recipeService.findAll()).thenReturn(Flux.just(Recipe()))

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Recipe::class.java) // throwing com.fasterxml.jackson.databind.exc.InvalidDefinitionException for org.bson.types.Binary (?!?)
    }
}