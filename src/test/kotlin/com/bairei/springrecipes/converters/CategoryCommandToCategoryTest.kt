package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.CategoryCommand
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class CategoryCommandToCategoryTest {

    private lateinit var converter : CategoryCommandToCategory

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = CategoryCommandToCategory()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(CategoryCommand()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val categoryCommand = CategoryCommand()
        categoryCommand.id = ID_VALUE
        categoryCommand.description = DESCRIPTION

        //when
        val category = converter.convert(categoryCommand)

        //then
        assertEquals(ID_VALUE, category!!.id)
        assertEquals(DESCRIPTION, category.description)
    }

    companion object {

        val ID_VALUE = "1"
        val DESCRIPTION = "description"
    }

}