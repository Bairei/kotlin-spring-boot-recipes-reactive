package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import com.bairei.springrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.bairei.springrecipes.repositories.reactive.UnitOfMeasureReactiveRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux


@Service
class UnitOfMeasureServiceImpl(private val unitOfMeasureRepository: UnitOfMeasureReactiveRepository, private val unitOfMeasureToUnitOfMeasureCommand: UnitOfMeasureToUnitOfMeasureCommand) : UnitOfMeasureService {

    override fun listAllUoms(): Flux<UnitOfMeasureCommand> = unitOfMeasureRepository.findAll()
            .map(unitOfMeasureToUnitOfMeasureCommand::convert)

//    override fun listAllUoms(): Flux<UnitOfMeasureCommand> = StreamSupport
//                .stream(unitOfMeasureRepository.findAll()
//                .spliterator(), false)
//                .map<UnitOfMeasureCommand>( { unitOfMeasureToUnitOfMeasureCommand.convert(it) })
//                .collect(toFlex())


}