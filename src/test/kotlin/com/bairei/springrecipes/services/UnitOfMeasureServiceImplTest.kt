package com.bairei.springrecipes.services

import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.domain.UnitOfMeasure
import com.bairei.springrecipes.repositories.reactive.UnitOfMeasureReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import reactor.core.publisher.Flux
import java.util.*

class UnitOfMeasureServiceImplTest {

    @Mock
    lateinit var unitOfMeasureRepository: UnitOfMeasureReactiveRepository

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

        `when`(unitOfMeasureRepository.findAll()).thenReturn(Flux.just(uom1,uom2))

        //when
        val commands = service.listAllUoms().collectList().block()

        //then
        assertEquals(2, commands!!.size.toLong())
        verify(unitOfMeasureRepository, times(1)).findAll()
    }

}