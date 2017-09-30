package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UnitOfMeasureCommandToUnitOfMeasureTest {

    lateinit var converter: UnitOfMeasureCommandToUnitOfMeasure

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = UnitOfMeasureCommandToUnitOfMeasure()

    }

    @Test
    @Throws(Exception::class)
    fun testNullParameter() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(UnitOfMeasureCommand()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val command = UnitOfMeasureCommand()
        command.id = LONG_VALUE
        command.description = DESCRIPTION

        //when
        val uom = converter.convert(command)

        //then
        assertNotNull(uom)
        assertEquals(LONG_VALUE, uom!!.id)
        assertEquals(DESCRIPTION, uom.description)

    }

    companion object {

        val DESCRIPTION = "description"
        val LONG_VALUE = "1"
    }

}