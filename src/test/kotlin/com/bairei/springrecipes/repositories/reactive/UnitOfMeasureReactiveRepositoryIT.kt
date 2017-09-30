package com.bairei.springrecipes.repositories.reactive

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
class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    lateinit var unitOfMeasureRepository: UnitOfMeasureReactiveRepository

    @Before
    fun setUp(){
        unitOfMeasureRepository.deleteAll().block()
    }

    @Test
    fun testUomSave(){
        val uom = UnitOfMeasure()
        uom.description = "Kilogram"
        unitOfMeasureRepository.save(uom).block()
        assertEquals(unitOfMeasureRepository.count().block(), 1L)
    }

    @Test
    fun testFindByDescription(){
        val uom = UnitOfMeasure()
        uom.description = "Each"

        unitOfMeasureRepository.save(uom).block()

        val fetchedUOM = unitOfMeasureRepository.findByDescription("Each").block()

        assertEquals(fetchedUOM!!.description, "Each")
    }
}