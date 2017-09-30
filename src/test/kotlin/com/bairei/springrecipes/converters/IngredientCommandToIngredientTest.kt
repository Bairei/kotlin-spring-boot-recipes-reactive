package com.bairei.springrecipes.converters

import com.bairei.springrecipes.commands.IngredientCommand
import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.domain.Recipe
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal
import org.junit.Assert.*

class IngredientCommandToIngredientTest {

    lateinit var converter: IngredientCommandToIngredient

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure())
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(IngredientCommand()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val command = IngredientCommand()
        command.id = ID_VALUE
        command.amount = AMOUNT
        command.description = DESCRIPTION
        val unitOfMeasureCommand = UnitOfMeasureCommand()
        unitOfMeasureCommand.id = UOM_ID
        command.uom = unitOfMeasureCommand

        //when
        val ingredient = converter.convert(command)

        //then
        assertNotNull(ingredient)
        assertNotNull(ingredient!!.uom)
        assertEquals(ID_VALUE, ingredient.id)
        assertEquals(AMOUNT, ingredient.amount)
        assertEquals(DESCRIPTION, ingredient.description)
        assertEquals(UOM_ID, ingredient.uom.id)
    }

    @Test
    @Throws(Exception::class)
    fun convertWithNullUOM() {
        //given
        val command = IngredientCommand()
        command.id = ID_VALUE
        command.amount = AMOUNT
        command.description = DESCRIPTION


        //when
        val ingredient = converter.convert(command)

        //then
        assertNotNull(ingredient!!)
        assertEquals(ingredient.uom.id, null)  // Expect initialized
        assertEquals(ingredient.uom.description, "") // UOM with null id and desc values
        assertEquals(ID_VALUE, ingredient.id)
        assertEquals(AMOUNT, ingredient.amount)
        assertEquals(DESCRIPTION, ingredient.description)

    }

    companion object {

        val RECIPE = Recipe()
        val AMOUNT = BigDecimal("1")
        val DESCRIPTION = "Cheeseburger"
        val ID_VALUE = "1"
        val UOM_ID = "2"
    }

}