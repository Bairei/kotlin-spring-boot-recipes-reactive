package com.bairei.springrecipes.services

import com.bairei.springrecipes.commands.UnitOfMeasureCommand

interface UnitOfMeasureService {
    fun listAllUoms() : Set<UnitOfMeasureCommand>
}