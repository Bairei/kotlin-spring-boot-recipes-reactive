package com.bairei.springrecipes.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Category (
        @Id var id: String? = null,
        var description: String = "",
        @DBRef var recipes: MutableSet<Recipe> = emptySet<Recipe>().toMutableSet()
)