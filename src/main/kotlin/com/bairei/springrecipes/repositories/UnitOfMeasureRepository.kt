package com.bairei.springrecipes.repositories

import com.bairei.springrecipes.domain.UnitOfMeasure
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UnitOfMeasureRepository: CrudRepository<UnitOfMeasure, String> {
    fun findByDescription(description: String): Optional<UnitOfMeasure>
}