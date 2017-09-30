package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.domain.UnitOfMeasure
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class UnitOfMeasureToUnitOfMeasureCommand : Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Nullable
    @Synchronized
    override fun convert(source: UnitOfMeasure?): UnitOfMeasureCommand? {
        if (source == null) return null

        val uomc = UnitOfMeasureCommand()
        uomc.description = source.description
        uomc.id = source.id
        return uomc
    }
}