package com.bairei.springrecipes.repositories.reactive

import com.bairei.springrecipes.domain.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface CategoryReactiveRepository: ReactiveMongoRepository<Category, String> {
    fun findByDescription (description: String) : Mono<Category>
}