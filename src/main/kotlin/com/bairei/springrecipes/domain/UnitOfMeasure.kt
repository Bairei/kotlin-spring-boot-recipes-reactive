package com.bairei.springrecipes.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UnitOfMeasure (@Id var id: String? = null,
                     var description: String = ""
)


