package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.CategoryCommand
import com.bairei.springrecipes.domain.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class CategoryCommandToCategory : Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    override fun convert(source: CategoryCommand?): Category? {
        if (source == null) {
            return null
        }

        val category = Category()

        category.id = source.id
        category.description = source.description

        return category
    }
}