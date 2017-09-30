package com.bairei.springrecipes.domain

import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlin.Exception


class CategoryTest {

    lateinit var category: Category

    @Before
    fun setUp() {
        this.category = Category()
    }

    @Test
    @Throws(Exception::class)
    fun getId() {
        val idValue = "4"
        category.id = idValue
        assertEquals(idValue, category.id)
    }

    @Test
    @Throws(Exception::class)
    fun getDescription() {

    }

    @Test
    @Throws(Exception::class)
    fun getRecipes() {

    }



}