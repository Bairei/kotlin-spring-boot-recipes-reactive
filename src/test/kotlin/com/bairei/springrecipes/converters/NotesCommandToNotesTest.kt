package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.NotesCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotesCommandToNotesTest {

    lateinit var converter: NotesCommandToNotes

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = NotesCommandToNotes()

    }

    @Test
    @Throws(Exception::class)
    fun testNullParameter() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(NotesCommand()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val notesCommand = NotesCommand()
        notesCommand.id = ID_VALUE
        notesCommand.recipeNotes = RECIPE_NOTES

        //when
        val notes = converter.convert(notesCommand)

        //then
        assertNotNull(notes)
        assertEquals(ID_VALUE, notes!!.id)
        assertEquals(RECIPE_NOTES, notes.recipeNotes)
    }

    companion object {

        val ID_VALUE = "1"
        val RECIPE_NOTES = "Notes"
    }

}