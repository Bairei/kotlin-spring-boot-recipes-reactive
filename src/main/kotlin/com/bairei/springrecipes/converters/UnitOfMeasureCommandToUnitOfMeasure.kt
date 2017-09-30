package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.domain.UnitOfMeasure
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class UnitOfMeasureCommandToUnitOfMeasure : Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    override fun convert(source: UnitOfMeasureCommand?): UnitOfMeasure? {
        if (source == null) return null

        val uom = UnitOfMeasure()
        uom.id = source.id
        uom.description = source.description
        return uom
    }
}