package com.bairei.springrecipes.commands

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class IngredientCommand {
    var id: String? = null
    var recipeId: String? = null
    @NotBlank var description: String = ""
    @Min(1) @NotNull var amount: BigDecimal = BigDecimal.ZERO
    @NotNull var uom: UnitOfMeasureCommand = UnitOfMeasureCommand()
}