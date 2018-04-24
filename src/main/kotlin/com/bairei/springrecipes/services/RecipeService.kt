package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.RecipeCommand
import com.bairei.springrecipes.domain.Recipe
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.collections.HashSet

interface RecipeService {
    fun findAll() : Flux<Recipe>?
    fun findById(id: String) : Mono<Recipe>?
    fun saveRecipeCommand(command: RecipeCommand?): Mono<RecipeCommand>
    fun findCommandById(id: String): Mono<RecipeCommand?>?
    fun deleteById(id:String): Mono<Void>?
}