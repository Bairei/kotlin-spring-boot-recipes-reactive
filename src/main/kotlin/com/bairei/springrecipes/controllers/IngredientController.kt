package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.services.IngredientService
import com.bairei.springrecipes.services.RecipeService
import com.bairei.springrecipes.services.UnitOfMeasureService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class IngredientController constructor(private val recipeService: RecipeService,
                                       private val ingredientService: IngredientService,
                                       private val unitOfMeasureService: UnitOfMeasureService) {

    private val log: Logger = LoggerFactory.getLogger(IngredientController::class.java)


    @GetMapping("/recipe/{recipeId}/ingredients")
    fun listIngredients(@PathVariable recipeId: String, model: Model): String {
        log.debug("Getting ingredient list for recipe id " + recipeId)

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(recipeId))
        return "recipe/ingredient/list"
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    fun showRecipeIngredient(@PathVariable recipeId: String,
                             @PathVariable id: String, model: Model) : String{
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id))
        return "recipe/ingredient/show"
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    fun updateRecipeIngredient(@PathVariable recipeId: String, @PathVariable id: String,
                               model: Model) : String{
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id))
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms())

        return "recipe/ingredient/ingredientform"
    }


    @GetMapping("recipe/{recipeId}/ingredient/new")
    fun newRecipe(@PathVariable recipeId: String, model: Model) : String {
        // making sure we have a good id value
        val recipeCommand = recipeService.findCommandById(recipeId)
        // todo raise exception if null

        // need to return back parent id for hidden form property
        val ingredientCommand = IngredientCommand()
        ingredientCommand.recipeId = recipeId
        model.addAttribute("ingredient", ingredientCommand)

        // init uom
        ingredientCommand.uom = UnitOfMeasureCommand()
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms())

        return "recipe/ingredient/ingredientform"
    }


    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    fun deleteRecipe(@PathVariable recipeId: String, @PathVariable id: String) : String{
        ingredientService.deleteIngredientFromRecipeById(recipeId, id)
        log.debug("Deleting ingredient id: $id, recipeId: $recipeId")
        return "redirect:/recipe/$recipeId/ingredients/"
    }


    @PostMapping("/recipe/{recipeId}/ingredient")
    fun saveOrUpdate(@ModelAttribute command: IngredientCommand) : String{
        val savedCommand = ingredientService.saveIngredientCommand(command)!!

        log.debug("saved recipe id: " + savedCommand.recipeId)
        log.debug("saved ingredient id: " + savedCommand.id)

        return "redirect:/recipe/${savedCommand.recipeId}/ingredient/${savedCommand.id}/show"
    }

}