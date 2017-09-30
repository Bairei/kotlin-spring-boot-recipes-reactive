package com.bairei.springrecipes.domain

import org.springframework.data.annotation.Id

class Notes(@Id var id: String? = null,
            var recipe: Recipe? = null,
            var recipeNotes: String = ""
)