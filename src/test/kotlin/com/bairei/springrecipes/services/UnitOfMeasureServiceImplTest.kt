package com.bairei.springrecipes.services

import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.domain.UnitOfMeasure
import com.bairei.springrecipes.repositories.UnitOfMeasureRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class UnitOfMeasureServiceImplTest {

    @Mock
    lateinit var unitOfMeasureRepository: UnitOfMeasureRepository

    lateinit var unitOfMeasureToUnitOfMeasureCommand : UnitOfMeasureToUnitOfMeasureCommand
    lateinit var service: UnitOfMeasureService

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        unitOfMeasureToUnitOfMeasureCommand = UnitOfMeasureToUnitOfMeasureCommand()
        service = UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand)


    }

    @Test
    @Throws(Exception::class)
    fun listAllUoms() {
        //given
        val unitOfMeasures = HashSet<UnitOfMeasure>()
        val uom1 = UnitOfMeasure()
        uom1.id = "1"
        unitOfMeasures.add(uom1)

        val uom2 = UnitOfMeasure()
        uom2.id = "2"
        unitOfMeasures.add(uom2)

        `when`(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures)

        //when
        val commands = service.listAllUoms()

        //then
        assertEquals(2, commands.size.toLong())
        verify<UnitOfMeasureRepository>(unitOfMeasureRepository, times(1)).findAll()
    }

}