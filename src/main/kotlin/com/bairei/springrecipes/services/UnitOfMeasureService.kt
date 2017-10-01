package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.UnitOfMeasureCommand
import reactor.core.publisher.Flux

interface UnitOfMeasureService {
    fun listAllUoms() : Flux<UnitOfMeasureCommand>
}