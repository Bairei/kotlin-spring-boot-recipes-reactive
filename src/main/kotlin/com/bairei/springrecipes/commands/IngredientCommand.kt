package com.bairei.springrecipes.commands

import java.math.BigDecimal

class IngredientCommand {
    var id: String? = null
    var recipeId: String? = null
    var description: String = ""
    var amount: BigDecimal = BigDecimal.ZERO
    var uom: UnitOfMeasureCommand = UnitOfMeasureCommand()
}