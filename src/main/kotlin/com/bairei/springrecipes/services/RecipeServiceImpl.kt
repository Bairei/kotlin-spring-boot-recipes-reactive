package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.converters.RecipeCommandToRecipe
import com.bairei.springrecipes.converters.RecipeToRecipeCommand
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
open class RecipeServiceImpl @Autowired constructor(private val recipeRepository: RecipeReactiveRepository,
                                               private val recipeCommandToRecipe: RecipeCommandToRecipe,
                                               private val recipeToRecipeCommand: RecipeToRecipeCommand) : RecipeService {

    override fun findById(id: String) = recipeRepository.findById(id)


    override fun findAll() = recipeRepository.findAll()

    override fun saveRecipeCommand(command: RecipeCommand?): Mono<RecipeCommand> {
        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert)
    }

    override fun findCommandById(id: String): Mono<RecipeCommand?>? = recipeRepository.findById(id)
            .map { recipe ->
                val recipeCommand = recipeToRecipeCommand.convert(recipe)
                recipeCommand?.ingredients?.forEach { rc ->
                    rc.recipeId = recipeCommand.id
                }
                return@map recipeCommand
            }

    override fun deleteById(id: String) = recipeRepository.deleteById(id)
}