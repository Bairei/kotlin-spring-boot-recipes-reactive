package com.bairei.springrecipes.commands

import com.bairei.springrecipes.domain.Difficulty
import org.bson.types.Binary
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

class RecipeCommand (
        var id: String = "",
        @get:NotBlank @get:Size(min = 3, max = 255) var description: String = "",
        @get:Min(1) @get:Max(999) var prepTime: Int = 0,
        @get:Min(1) @get:Max(999) var cookTime: Int = 0,
        @get:Min(1) @get:Max(100) var servings: Int = 0,
        var source: String = "",
        @get:URL var url: String = "",
        @get:NotBlank var directions: String = "",
        var ingredients: MutableList<IngredientCommand> = ArrayList(),
        var difficulty: Difficulty = Difficulty.EASY,
        var notes : NotesCommand = NotesCommand(),
        var image : Binary = Binary(ByteArray(0)),
        var categories : MutableList<CategoryCommand> = ArrayList()
)

// @get annotations are necessary because the fields are initialized in CONSTRUCTOR, rather than in class itself
// see: https://kotlinlang.org/docs/reference/annotations.html#annotation-use-site-targets