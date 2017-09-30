package com.bairei.springrecipes.converters

import com.bairei.springrecipes.domain.Ingredient
import com.bairei.springrecipes.domain.Recipe
import com.bairei.springrecipes.domain.UnitOfMeasure
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal

import org.junit.Assert.*

class IngredientToIngredientCommandTest {


    lateinit var converter: IngredientToIngredientCommand

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand())
    }

    @Test
    @Throws(Exception::class)
    fun testNullConvert() {
        assertNull(converter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter.convert(Ingredient()))
    }

    @Test
    @Throws(Exception::class)
    fun testConvertNullUOM() {
        //given
        val ingredient = Ingredient()
        ingredient.id = ID_VALUE
//        ingredient.recipe = RECIPE
        ingredient.amount = AMOUNT
        ingredient.description = DESCRIPTION
        //        ingredient.uom = null
        //when
        val ingredientCommand = converter.convert(ingredient)
        //then
        assertEquals(ingredientCommand!!.uom.id, null) // Assert that has uom
        assertEquals(ingredientCommand.uom.description, "") // with default values (id = null, desc ="")
        assertEquals(ID_VALUE, ingredientCommand.id)
        // assertEquals(RECIPE, ingredientCommand.get);
        assertEquals(AMOUNT, ingredientCommand.amount)
        assertEquals(DESCRIPTION, ingredientCommand.description)
    }

    @Test
    @Throws(Exception::class)
    fun testConvertWithUom() {
        //given
        val ingredient = Ingredient()
        ingredient.id = ID_VALUE
//        ingredient.recipe = RECIPE
        ingredient.amount = AMOUNT
        ingredient.description = DESCRIPTION

        val uom = UnitOfMeasure()
        uom.id = UOM_ID

        ingredient.uom = uom
        //when
        val ingredientCommand = converter.convert(ingredient)
        //then
        assertEquals(ID_VALUE, ingredientCommand!!.id)
        assertNotNull(ingredientCommand.uom)
        assertEquals(UOM_ID, ingredientCommand.uom.id)
        // assertEquals(RECIPE, ingredientCommand.get);
        assertEquals(AMOUNT, ingredientCommand.amount)
        assertEquals(DESCRIPTION, ingredientCommand.description)
    }

    companion object {

        val RECIPE = Recipe()
        val AMOUNT = BigDecimal("1")
        val DESCRIPTION = "Cheeseburger"
        val UOM_ID = "2"
        val ID_VALUE = "1"
    }
}