package com.bairei.springrecipes.repositories

import com.bairei.springrecipes.domain.Recipe
import org.springframework.data.repository.CrudRepository

interface RecipeRepository: CrudRepository<Recipe, String> {
}