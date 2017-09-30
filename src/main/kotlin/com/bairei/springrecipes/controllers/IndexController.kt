package com.bairei.springrecipes.controllers

import com.bairei.springrecipes.services.RecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class IndexController @Autowired constructor(private val recipeService: RecipeService) {

    @RequestMapping("/","","/index")
    fun getIndexPage(model: Model): String {
        model.addAttribute("recipes", recipeService.findAll())
        return "index"
    }
}