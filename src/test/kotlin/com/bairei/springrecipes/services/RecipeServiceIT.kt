package com.bairei.springrecipes.services

import com.bairei.springrecipes.converters.RecipeCommandToRecipe
import com.bairei.springrecipes.converters.RecipeToRecipeCommand
import com.bairei.springrecipes.repositories.RecipeRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

import org.junit.Assert.assertEquals
import org.junit.Ignore


/**
 * Created by jt on 6/21/17.
 */
@Ignore
@RunWith(SpringRunner::class)
@SpringBootTest
class RecipeServiceIT { // since 1.2-M2 it's required to give it 'open' prefix, otherwise
                             // it throws IllegalArgumentException (cannot subclass final class)
    @Autowired
    private lateinit var recipeService: RecipeService

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Autowired
    private lateinit var recipeCommandToRecipe: RecipeCommandToRecipe

    @Autowired
    private lateinit var recipeToRecipeCommand: RecipeToRecipeCommand

    @Test
    @Throws(Exception::class)
    fun testSaveOfDescription() {
        //given
        val recipes = recipeRepository.findAll()
        val testRecipe = recipes.iterator().next()
        val testRecipeCommand = recipeToRecipeCommand.convert(testRecipe)

        //when
        testRecipeCommand!!.description = NEW_DESCRIPTION
        val savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand)

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.description)
        assertEquals(testRecipe.id, savedRecipeCommand.id)
        assertEquals(testRecipe.categories.size.toLong(), savedRecipeCommand.categories.size.toLong())
        assertEquals(testRecipe.ingredients.size.toLong(), savedRecipeCommand.ingredients.size.toLong())
    }

    companion object {

        val NEW_DESCRIPTION = "New Description"
    }
}