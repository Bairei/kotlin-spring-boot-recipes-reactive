package com.bairei.springrecipes.repositories.reactive

import com.bairei.springrecipes.domain.Recipe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface RecipeReactiveRepository: ReactiveMongoRepository<Recipe, String> {
}