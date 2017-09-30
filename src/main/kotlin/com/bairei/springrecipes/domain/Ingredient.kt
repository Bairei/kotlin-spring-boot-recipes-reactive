package com.bairei.springrecipes.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import java.math.BigDecimal
import java.util.*

class Ingredient (@Id var id: String = UUID.randomUUID().toString(),
                  var description: String = "",
                  var amount: BigDecimal = BigDecimal.ZERO,
                  @DBRef var uom: UnitOfMeasure = UnitOfMeasure()
//                  var recipe: Recipe? = null
)