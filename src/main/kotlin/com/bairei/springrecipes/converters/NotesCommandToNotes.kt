package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.NotesCommand
import com.bairei.springrecipes.domain.Notes
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class NotesCommandToNotes : Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    override fun convert(source: NotesCommand?): Notes? {
        if (source == null) return null
        val notes = Notes()
        notes.id = source.id
        notes.recipeNotes = source.recipeNotes
        return notes
    }
}