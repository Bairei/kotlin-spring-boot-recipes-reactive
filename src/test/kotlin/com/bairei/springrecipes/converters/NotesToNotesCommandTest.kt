package com.bairei.springrecipes.converters

import com.bairei.springrecipes.domain.Notes
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotesToNotesCommandTest {
    lateinit var converter: NotesToNotesCommand

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = NotesToNotesCommand()
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val notes = Notes()
        notes.id = ID_VALUE
        notes.recipeNotes = RECIPE_NOTES

        //when
        val notesCommand = converter.convert(notes)

        //then
        assertEquals(ID_VALUE, notesCommand!!.id)
        assertEquals(RECIPE_NOTES, notesCommand.recipeNotes)
    }

    @Test
    @Throws(Exception::class)
    fun testNull() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(Notes()))
    }

    companion object {

        val ID_VALUE = "1"
        val RECIPE_NOTES = "Notes"
    }
}