package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.converters.RecipeCommandToRecipe
import com.bairei.springrecipes.converters.RecipeToRecipeCommand
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.exceptions.NotFoundException
import com.bairei.springrecipes.repositories.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeServiceImpl @Autowired constructor(private val recipeRepository: RecipeRepository,
                                               private val recipeCommandToRecipe: RecipeCommandToRecipe,
                                               private val recipeToRecipeCommand: RecipeToRecipeCommand) : RecipeService {

    override fun findById(id: String): Recipe {
        val recipeOptional = recipeRepository.findById(id)
        if (!recipeOptional.isPresent){
            // throwRuntimeException("Recipe not found")
            throw NotFoundException("Recipe not found. For ID value: $id")
        }
        return recipeOptional.get()
    }

    override fun findAll(): Set<Recipe> = recipeRepository.findAll().map { it }.toHashSet()

    @Transactional
    override fun saveRecipeCommand(command: RecipeCommand?): RecipeCommand {
        val detachedRecipe = recipeCommandToRecipe.convert(command)

        val savedRecipe = recipeRepository.save(detachedRecipe)
        return recipeToRecipeCommand.convert(savedRecipe)!!
    }

    @Transactional
    override fun findCommandById(id: String): RecipeCommand? = recipeToRecipeCommand.convert(findById(id))

    override fun deleteById(id: String) = recipeRepository.deleteById(id)
}