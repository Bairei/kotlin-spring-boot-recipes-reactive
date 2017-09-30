package com.bairei.springrecipes.bootstrap

import com.bairei.springrecipes.domain.*
import com.bairei.springrecipes.repositories.CategoryRepository
import com.bairei.springrecipes.repositories.RecipeRepository
import com.bairei.springrecipes.repositories.UnitOfMeasureRepository
import com.bairei.springrecipes.repositories.reactive.CategoryReactiveRepository
import com.bairei.springrecipes.repositories.reactive.RecipeReactiveRepository
import com.bairei.springrecipes.repositories.reactive.UnitOfMeasureReactiveRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*


@Component
class RecipeBootstrap @Autowired constructor(val recipeRepository: RecipeRepository,
                                             val categoryRepository: CategoryRepository,
                                             val unitOfMeasureRepository: UnitOfMeasureRepository
                                            ) : ApplicationListener<ContextRefreshedEvent> {

    private val log : Logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var reactiveUnitOfMeasureRepository : UnitOfMeasureReactiveRepository

    @Autowired
    lateinit var reactiveCategoryRepository: CategoryReactiveRepository

    @Autowired
    lateinit var reactiveRecipeRepository: RecipeReactiveRepository

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        loadCategories()
        loadUom()
        recipeRepository.saveAll(getRecipes())
        log.debug("loading data from RecipeBootstrap")
        log.debug("######")
        log.debug("UOM count: ${reactiveUnitOfMeasureRepository.count().block().toString()}")
        log.debug("Category count: ${reactiveCategoryRepository.count().block().toString()}")
        log.debug("Recipe count: ${reactiveRecipeRepository.count().block().toString()}")
    }

    private fun getRecipes(): List<Recipe> {

        val recipes = ArrayList<Recipe>(2)

        //get UOMs
        val eachUomOptional = unitOfMeasureRepository.findByDescription("Each")

        if (!eachUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        val tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon")

        if (!tableSpoonUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        val teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon")

        if (!teaSpoonUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        val dashUomOptional = unitOfMeasureRepository.findByDescription("Dash")

        if (!dashUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        val pintUomOptional = unitOfMeasureRepository.findByDescription("Pint")

        if (!pintUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        val cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup")

        if (!cupsUomOptional.isPresent) {
            throw RuntimeException("Expected UOM Not Found")
        }

        //get optionals
        val eachUom = eachUomOptional.get()
        val tableSpoonUom = tableSpoonUomOptional.get()
        val teapoonUom = tableSpoonUomOptional.get()
        val dashUom = dashUomOptional.get()
        val pintUom = dashUomOptional.get()
        val cupsUom = cupsUomOptional.get()

        //get Categories
        val americanCategoryOptional = categoryRepository.findByDescription("American")

        if (!americanCategoryOptional.isPresent) {
            throw RuntimeException("Expected Category Not Found")
        }

        val mexicanCategoryOptional = categoryRepository.findByDescription("Mexican")

        if (!mexicanCategoryOptional.isPresent) {
            throw RuntimeException("Expected Category Not Found")
        }

        val americanCategory = americanCategoryOptional.get()
        val mexicanCategory = mexicanCategoryOptional.get()

        //Yummy Guac
        val guacRecipe = Recipe()
        guacRecipe.description = "Perfect Guacamole"
        guacRecipe.prepTime = 10
        guacRecipe.cookTime = 0
        guacRecipe.difficulty = Difficulty.EASY
        guacRecipe.directions = "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd"

        val guacNotes = Notes()
        guacNotes.recipeNotes = "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws"

        guacRecipe.notes = guacNotes

        //very redundent - could add helper method, and make this simpler
        guacRecipe.addIngredient(Ingredient(description = "ripe avocados", amount = BigDecimal(2), uom = eachUom))
        guacRecipe.addIngredient(Ingredient(description = "Kosher salt", amount = BigDecimal(".5"), uom = teapoonUom))
        guacRecipe.addIngredient(Ingredient(description = "fresh lime juice or lemon juice", amount = BigDecimal(2), uom = tableSpoonUom))
        guacRecipe.addIngredient(Ingredient(description = "minced red onion or thinly sliced green onion", amount = BigDecimal(2), uom = tableSpoonUom))
        guacRecipe.addIngredient(Ingredient(description = "serrano chiles, stems and seeds removed, minced", amount = BigDecimal(2), uom = eachUom))
        guacRecipe.addIngredient(Ingredient(description = "Cilantro", amount = BigDecimal(2), uom = tableSpoonUom))
        guacRecipe.addIngredient(Ingredient(description = "freshly grated black pepper", amount = BigDecimal(2), uom = dashUom))
        guacRecipe.addIngredient(Ingredient(description = "ripe tomato, seeds and pulp removed, chopped", amount = BigDecimal(".5"), uom = eachUom))

        guacRecipe.categories.add(americanCategory)
        guacRecipe.categories.add(mexicanCategory)

        guacRecipe.url = "http://www.simplyrecipes.com/recipes/perfect_guacamole/"
        guacRecipe.servings = 4
        guacRecipe.source = "Simply Recipes"

        //add to return list
        recipes.add(guacRecipe)

        //Yummy Tacos
        val tacosRecipe = Recipe()
        tacosRecipe.description = "Spicy Grilled Chicken Taco"
        tacosRecipe.cookTime = 9
        tacosRecipe.prepTime = 20
        tacosRecipe.difficulty = Difficulty.MODERATE

        tacosRecipe.directions = "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm"

        val tacoNotes = Notes()
        tacoNotes.recipeNotes = "We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ"

        tacosRecipe.notes = tacoNotes

        tacosRecipe.addIngredient(Ingredient(description = "Ancho Chili Powder",amount =  BigDecimal(2), uom = tableSpoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Dried Oregano",amount =  BigDecimal(1), uom =  teapoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Dried Cumin",amount =  BigDecimal(1), uom = teapoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Sugar",amount =  BigDecimal(1), uom = teapoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Salt",amount =  BigDecimal(".5"), uom = teapoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Clove of Garlic, Choppedr",amount =  BigDecimal(1), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "finely grated orange zestr",amount =  BigDecimal(1), uom = tableSpoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "fresh-squeezed orange juice",amount =  BigDecimal(3), uom = tableSpoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "Olive Oil",amount =  BigDecimal(2), uom = tableSpoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "boneless chicken thighs",amount =  BigDecimal(4), uom = tableSpoonUom))
        tacosRecipe.addIngredient(Ingredient(description = "small corn tortillasr",amount =  BigDecimal(8), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "packed baby arugula",amount =  BigDecimal(3), uom = cupsUom))
        tacosRecipe.addIngredient(Ingredient(description = "medium ripe avocados, slic",amount =  BigDecimal(2), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "radishes, thinly sliced",amount =  BigDecimal(4), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "cherry tomatoes, halved",amount =  BigDecimal(".5"), uom = pintUom))
        tacosRecipe.addIngredient(Ingredient(description = "red onion, thinly sliced",amount =  BigDecimal(".25"), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "Roughly chopped cilantro",amount =  BigDecimal(4), uom = eachUom))
        tacosRecipe.addIngredient(Ingredient(description = "cup sour cream thinned with 1/4 cup milk",amount =  BigDecimal(4), uom = cupsUom))
        tacosRecipe.addIngredient(Ingredient(description = "lime, cut into wedges",amount = BigDecimal(4), uom = eachUom))

        tacosRecipe.categories.add(americanCategory)
        tacosRecipe.categories.add(mexicanCategory)

        tacosRecipe.url = "http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/"
        tacosRecipe.servings = 4
        tacosRecipe.source = "Simply Recipes"

        recipes.add(tacosRecipe)
        return recipes
    }

    private fun loadCategories() {
        val cat1 = Category()
        cat1.description = "American"
        categoryRepository.save(cat1)

        val cat2 = Category()
        cat2.description = "Italian"
        categoryRepository.save(cat2)

        val cat3 = Category()
        cat3.description = "Mexican"
        categoryRepository.save(cat3)

        val cat4 = Category()
        cat4.description = "Fast Food"
        categoryRepository.save(cat4)
    }

    private fun loadUom() {
        val uom1 = UnitOfMeasure()
        uom1.description = "Teaspoon"
        unitOfMeasureRepository.save(uom1)

        val uom2 = UnitOfMeasure()
        uom2.description = "Tablespoon"
        unitOfMeasureRepository.save(uom2)

        val uom3 = UnitOfMeasure()
        uom3.description = "Cup"
        unitOfMeasureRepository.save(uom3)

        val uom4 = UnitOfMeasure()
        uom4.description = "Pinch"
        unitOfMeasureRepository.save(uom4)

        val uom5 = UnitOfMeasure()
        uom5.description = "Ounce"
        unitOfMeasureRepository.save(uom5)

        val uom6 = UnitOfMeasure()
        uom6.description = "Each"
        unitOfMeasureRepository.save(uom6)

        val uom7 = UnitOfMeasure()
        uom7.description = "Pint"
        unitOfMeasureRepository.save(uom7)

        val uom8 = UnitOfMeasure()
        uom8.description = "Dash"
        unitOfMeasureRepository.save(uom8)
    }



}