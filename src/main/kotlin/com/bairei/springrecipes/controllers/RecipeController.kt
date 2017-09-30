package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.exceptions.NotFoundException
import com.bairei.springrecipes.services.RecipeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

/**
 * created by bairei on 21/09/17.
 */
@Controller
class RecipeController @Autowired constructor(val recipeService: RecipeService){

    private val log: Logger = LoggerFactory.getLogger(this::class.java)


    @GetMapping("/recipe/{id}/show")
    fun showById(@PathVariable id: String, model:Model) : String {
        model.addAttribute("recipe", recipeService.findById(id))

        return "recipe/show"
    }


    @GetMapping("recipe/new")
    fun newRecipe(model : Model) : String {
        model.addAttribute("recipe", Recipe())
        return RECIPE_RECIPEFORM_URL
    }


    @GetMapping("recipe/{id}/update")
    fun updateRecipe (@PathVariable id: String, model: Model): String {
        model.addAttribute("recipe", recipeService.findCommandById(id))
        return RECIPE_RECIPEFORM_URL
    }


    @PostMapping("recipe")
    fun saveOrUpdate(@Valid @ModelAttribute("recipe") command : RecipeCommand, bindingResult: BindingResult) : String {

        if(bindingResult.hasErrors()){
            bindingResult.allErrors.forEach({objecterror -> log.debug(objecterror.toString())})

            return RECIPE_RECIPEFORM_URL
        }

        val savedCommand = recipeService.saveRecipeCommand(command)
        return "redirect:/recipe/${savedCommand.id}/show"
    }


    @GetMapping("recipe/{id}/delete")
    fun deleteRecipe(@PathVariable id: String): String{
        recipeService.deleteById(id)
        log.debug("Deleting id: " + id)
        return "redirect:/"
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: Exception) : ModelAndView{
        log.error("Handling not found exception")
        log.error(ex.message)
        val modelAndView = ModelAndView()
        modelAndView.viewName = "404error"
        modelAndView.addObject("exception", ex)
        return modelAndView
    }

    companion object {
        val RECIPE_RECIPEFORM_URL = "recipe/recipeform"
    }

}