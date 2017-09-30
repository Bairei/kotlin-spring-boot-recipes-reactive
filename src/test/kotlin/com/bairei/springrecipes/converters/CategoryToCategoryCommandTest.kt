package com.bairei.springrecipes.converters

import com.bairei.springrecipes.domain.Category
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class CategoryToCategoryCommandTest {
    lateinit var convter: CategoryToCategoryCommand

    @Before
    @Throws(Exception::class)
    fun setUp() {
        convter = CategoryToCategoryCommand()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(convter.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(convter.convert(Category()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
        //given
        val category = Category()
        category.id = ID_VALUE
        category.description = DESCRIPTION

        //when
        val categoryCommand = convter.convert(category)

        //then
        assertEquals(ID_VALUE, categoryCommand!!.id)
        assertEquals(DESCRIPTION, categoryCommand.description)

    }

    companion object {

        val ID_VALUE = "1"
        val DESCRIPTION = "descript"
    }

}