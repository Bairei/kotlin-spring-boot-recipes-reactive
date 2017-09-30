package com.bairei.springrecipes.repositories.reactive

import com.bairei.springrecipes.domain.UnitOfMeasure
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UnitOfMeasureReactiveRepository : ReactiveMongoRepository<UnitOfMeasure, String>{
    fun findByDescription(description: String): Mono<UnitOfMeasure>
}