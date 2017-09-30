package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.converters.IngredientCommandToIngredient
import com.bairei.springrecipes.converters.IngredientToIngredientCommand
import com.bairei.springrecipes.repositories.RecipeRepository
import com.bairei.springrecipes.repositories.UnitOfMeasureRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IngredientServiceImpl (private val ingredientToIngredientCommand: IngredientToIngredientCommand,
                             private val recipeRepository: RecipeRepository,
                             private val ingredientCommandToIngredient: IngredientCommandToIngredient,
                             private val unitOfMeasureRepository: UnitOfMeasureRepository) : IngredientService {


    private val log : Logger = LoggerFactory.getLogger(this::class.java)

    override fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): IngredientCommand {
        val recipeOptional = recipeRepository.findById(recipeId)

        if (!recipeOptional.isPresent){
            // todo implement error handling
            log.error("recipe id not found ID: " + recipeId)
        }

        val recipe = recipeOptional.get()

        val ingredientCommandOptional = recipe.ingredients.stream()
                .filter({ingredient -> ingredient.id.equals(ingredientId)})
                .map { ingredient -> ingredientToIngredientCommand.convert(ingredient)}.findFirst()

        if(!ingredientCommandOptional.isPresent){
            // todo implement error handling
            log.error("Ingredient id not found: " + ingredientId)
        }

        return ingredientCommandOptional.get()
    }

    @Transactional
    override fun saveIngredientCommand(command: IngredientCommand): IngredientCommand? {
        val recipeOptional = recipeRepository.findById(command.recipeId)

        if (!recipeOptional.isPresent) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.recipeId)
            return IngredientCommand()
        } else {
            val recipe = recipeOptional.get()

            val ingredientOptional = recipe
                    .ingredients
                    .stream()
                    .filter { ingredient -> ingredient.id.equals(command.id) }
                    .findFirst()

            if (ingredientOptional.isPresent) {
                val ingredientFound = ingredientOptional.get()
                ingredientFound.description = command.description
                ingredientFound.amount = command.amount
                ingredientFound.uom = unitOfMeasureRepository
                        .findById(command.uom.id)
                        .orElseThrow { RuntimeException("UOM NOT FOUND") } //todo address this
            } else {
                //add new Ingredient
                val ingredient = ingredientCommandToIngredient.convert(command)
//                ingredient!!.recipe = recipe
                recipe.addIngredient(ingredient!!)
            }

            val savedRecipe = recipeRepository.save(recipe)


            var savedIngredientOptional = savedRecipe.ingredients.stream()
                    .filter { recipeIngredients -> recipeIngredients.id.equals(command.id) }
                    .findFirst()

            if (!savedIngredientOptional.isPresent) {
                // not totally safe, but best guess...
                savedIngredientOptional = savedRecipe.ingredients.stream()
                        .filter({ recipeIngredients -> recipeIngredients.description.equals(command.description)})
                        .filter({ recipeIngredients -> recipeIngredients.amount.equals(command.amount) })
                        .filter({ recipeIngredients -> recipeIngredients.uom.id.equals(command.uom.id)})
                        .findFirst()
            }

            //todo check for fail
           return ingredientToIngredientCommand.convert(savedIngredientOptional.get())

        }
    }

    override fun deleteIngredientFromRecipeById(recipeId: String, id: String) {
        val recipeOptional = recipeRepository.findById(recipeId)
        if (recipeOptional.isPresent){
            log.debug("filtering ingredients which id != $id")
            val recipeToSave = recipeOptional.get()

            val ingredientOptional = recipeToSave
                    .ingredients.stream()
                    .filter({ingredient -> ingredient.id.equals(id)})
                    .findFirst()

            if(ingredientOptional.isPresent){
                log.debug("found ingredient with id $id")
                val ingredientToDelete = ingredientOptional.get()
                recipeToSave.ingredients.remove(ingredientToDelete)
                recipeRepository.save(recipeToSave)
                return
            }
            log.debug("recipe with id $recipeId was not found")

        }
        log.info("no recipe with id = $recipeId was found")
    }
}