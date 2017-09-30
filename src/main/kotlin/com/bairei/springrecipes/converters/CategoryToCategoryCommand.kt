package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.CategoryCommand
import com.bairei.springrecipes.domain.Category
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class CategoryToCategoryCommand : Converter<Category,CategoryCommand> {


    @Synchronized
    @Nullable
    override fun convert(source: Category?): CategoryCommand? {
        if (source == null) return null

        val categoryCommand = CategoryCommand()

        categoryCommand.id = source.id
        categoryCommand.description = source.description

        return categoryCommand
    }
}