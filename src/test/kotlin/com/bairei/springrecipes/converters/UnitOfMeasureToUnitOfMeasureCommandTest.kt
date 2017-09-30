package com.bairei.springrecipes.converters

import com.bairei.springrecipes.domain.UnitOfMeasure
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UnitOfMeasureToUnitOfMeasureCommandTest {

    lateinit var converter: UnitOfMeasureToUnitOfMeasureCommand

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = UnitOfMeasureToUnitOfMeasureCommand()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObjectConvert() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObj() {
        assertNotNull(converter.convert(UnitOfMeasure()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val uom = UnitOfMeasure()
        uom.id = LONG_VALUE
        uom.description = DESCRIPTION
        //when
        val uomc = converter.convert(uom)

        //then
        assertEquals(LONG_VALUE, uomc!!.id)
        assertEquals(DESCRIPTION, uomc.description)
    }

    companion object {

        val DESCRIPTION = "description"
        val LONG_VALUE = "1"
    }

}