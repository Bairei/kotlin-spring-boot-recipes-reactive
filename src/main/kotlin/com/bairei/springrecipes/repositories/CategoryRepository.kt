package com.bairei.springrecipes.repositories

import com.bairei.springrecipes.domain.Category
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CategoryRepository: CrudRepository<Category, String> {
    fun findByDescription (description: String) : Optional<Category>
}