package com.bairei.springrecipes.repositories.reactive

import com.bairei.springrecipes.domain.Category
import com.bairei.springrecipes.domain.UnitOfMeasure
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
class CategoryReactiveRepositoryIT {

    @Autowired
    lateinit var categoryRepository : CategoryReactiveRepository

    @Before
    fun setUp(){
        categoryRepository.deleteAll().block()
    }

    @Test
    fun testCategorySave(){
        val category = Category(description = "some category")
        categoryRepository.save(category).block()

        assertEquals(categoryRepository.count().block(), 1L)
    }

    @Test
    fun testFindByDescription(){
        val category = Category(description = "Italian")

        categoryRepository.save(category).block()

        val fetchedCategory = categoryRepository.findByDescription("Italian").block()

        assertEquals(fetchedCategory!!.description, "Italian")
    }


}