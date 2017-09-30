package com.bairei.springrecipes.repositories.reactive

import com.bairei.springrecipes.bootstrap.RecipeBootstrap
import com.bairei.springrecipes.domain.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@DataMongoTest
class RecipeReactiveRepositoryIT {
    @Autowired
    lateinit var recipeRepository: RecipeReactiveRepository

    @Before
    fun setUp(){
        recipeRepository.deleteAll().block()
    }

    @Test
    fun newObjectTest(){
        val recipe = Recipe()
        recipe.description = "Yummy"
        recipeRepository.save(recipe).block()

        val count = recipeRepository.count().block()

        assertEquals(1L, count)
    }

}