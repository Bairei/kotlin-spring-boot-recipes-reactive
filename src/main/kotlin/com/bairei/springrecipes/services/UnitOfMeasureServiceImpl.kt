package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.repositories.UnitOfMeasureRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors.toSet
import java.util.stream.StreamSupport


@Service
class UnitOfMeasureServiceImpl(private val unitOfMeasureRepository: UnitOfMeasureRepository, private val unitOfMeasureToUnitOfMeasureCommand: UnitOfMeasureToUnitOfMeasureCommand) : UnitOfMeasureService {

    override fun listAllUoms(): Set<UnitOfMeasureCommand> {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map<UnitOfMeasureCommand>( { unitOfMeasureToUnitOfMeasureCommand.convert(it) })
                .collect(toSet())
    }
}