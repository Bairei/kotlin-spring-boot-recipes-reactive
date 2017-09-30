package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.NotesCommand
import com.bairei.springrecipes.domain.Notes
import org.springframework.core.convert.converter.Converter
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component

@Component
class NotesToNotesCommand : Converter<Notes,NotesCommand> {

    @Nullable
    @Synchronized
    override fun convert(source: Notes?): NotesCommand? {
        if (source == null) return null

        val notesCommand = NotesCommand()
        notesCommand.id = source.id
        notesCommand.recipeNotes = source.recipeNotes
        return notesCommand
    }
}