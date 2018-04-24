package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.converters.IngredientCommandToIngredient
import com.bairei.springrecipes.converters.IngredientToIngredientCommand
import com.bairei.springrecipes.domain.Ingredient
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import com.bairei.springrecipes.repositories.reactive.UnitOfMeasureReactiveRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
open class IngredientServiceImpl (private val ingredientToIngredientCommand: IngredientToIngredientCommand,
                             private val ingredientCommandToIngredient: IngredientCommandToIngredient,
                             private val unitOfMeasureRepository: UnitOfMeasureReactiveRepository,
                             private val recipeReactiveRepository: RecipeReactiveRepository) : IngredientService {


    private val log : Logger = LoggerFactory.getLogger(this::class.java)

    private fun transform(ingredient: Ingredient, recipeId: String): IngredientCommand{
        val command = ingredientToIngredientCommand.convert(ingredient)
        command!!.recipeId = recipeId
        return command
    }

    override fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): Mono<IngredientCommand>
            = recipeReactiveRepository.findById(recipeId)
            .flatMapIterable(Recipe::ingredients)
            .filter({ingredient -> ingredient.id.equals(ingredientId,true)})
            .single()
            .map({ingredient -> transform(ingredient, recipeId)})


    override fun saveIngredientCommand(command: IngredientCommand): Mono<IngredientCommand> {
        val recipe = recipeReactiveRepository.findById(command.recipeId).block()

        if (recipe == null) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.recipeId)
            return Mono.just(IngredientCommand())
        } else {

            val ingredientOptional = recipe
                    .ingredients
                    .stream()
                    .filter { ingredient -> ingredient.id == command.id }
                    .findFirst()

            if (ingredientOptional.isPresent) {
                val ingredientFound = ingredientOptional.get()
                ingredientFound.description = command.description
                ingredientFound.amount = command.amount
                ingredientFound.uom = unitOfMeasureRepository
                        .findById(command.uom.id).block()!!

                //        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
                if (ingredientFound.uom == null) {
                    RuntimeException("UOM NOT FOUND")
                }
            } else {
                //add new Ingredient
                val ingredient = ingredientCommandToIngredient.convert(command)
                recipe.addIngredient(ingredient!!)
            }

            val savedRecipe = recipeReactiveRepository.save(recipe).block()

            var savedIngredientOptional = savedRecipe!!.ingredients.stream()
                    .filter { recipeIngredients -> recipeIngredients.id == command.id }
                    .findFirst()

            //check by description
            if (!savedIngredientOptional.isPresent) {
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.ingredients.stream()
                        .filter { recipeIngredients -> recipeIngredients.description == command.description }
                        .filter { recipeIngredients -> recipeIngredients.amount == command.amount }
                        .filter { recipeIngredients -> recipeIngredients.uom.id == command.uom.id }
                        .findFirst()
            }

            //todo check for fail

            //enhance with id value
            val ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get())!!
            ingredientCommandSaved.recipeId = recipe.id

            return Mono.just(ingredientCommandSaved)
        }

    }

    override fun deleteById(recipeId: String, idToDelete: String): Mono<Unit> {

        log.debug("Deleting ingredient: $recipeId:$idToDelete")

        val recipe = recipeReactiveRepository.findById(recipeId).block()

        if (recipe != null) {

            log.debug("found recipe")

            val ingredientOptional = recipe
                    .ingredients
                    .stream()
                    .filter { ingredient -> ingredient.id == idToDelete }
                    .findFirst()

            if (ingredientOptional.isPresent) {
                log.debug("found Ingredient")

                recipe.ingredients.remove(ingredientOptional.get())
                recipeReactiveRepository.save(recipe).block()
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId)
        }
        return Mono.empty()
    }
}