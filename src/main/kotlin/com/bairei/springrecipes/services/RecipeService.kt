package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Recipe
import java.util.*
import kotlin.collections.HashSet

interface RecipeService {
    fun findAll() : Set<Recipe>

    fun findById(id: String) : Recipe

    fun saveRecipeCommand(command: RecipeCommand?): RecipeCommand
    fun findCommandById(id: String): RecipeCommand?
    fun deleteById(id:String)
}