package com.bairei.springrecipes.domain

import org.bson.types.Binary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Recipe (@Id var id: String? = null,
              var description: String = "",
              var prepTime: Int = 0,
              var cookTime: Int = 0,
              var servings: Int = 0,
              var source: String = "",
              var url: String = "",
              var directions: String = "",
              var difficulty: Difficulty = Difficulty.EASY,
              var ingredients: MutableSet<Ingredient> = emptySet<Ingredient>().toMutableSet(),
              var image: Binary = Binary(ByteArray(0)),
              var notes: Notes = Notes(),
              @DBRef var categories: MutableSet<Category> = emptySet<Category>().toHashSet()
){
    fun addIngredient(ingredient: Ingredient): Recipe {
//        ingredient.recipe = this
        this.ingredients.add(ingredient)
        return this
    }

//    override fun toString(): String {
//        return "[id=$id, description =$description, prepTime=$prepTime, cookTime=$cookTime, servings=$servings, source= $source," +
//                " "
//    }
}