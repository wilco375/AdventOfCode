import java.lang.Long.sum

class Day14 : AbstractDay() {
    override fun run() = sequence<Any> {
        val recipes = readInputFile("Day14.txt")
            .split("\n")
            .map { Recipe.fromString(it) }

        fun findRecipe(result: String): Recipe {
            return recipes.first { it.result.name == result }
        }

        val stock = HashMap<String, Long>()
        fun getRequiredOre(ingredient: Ingredient): Long {
            if (ingredient.name == "ORE") {
                return ingredient.amount
            }

            var neededAmount = ingredient.amount
            val inStockAmount = stock.getOrDefault(ingredient.name, 0)
            when {
                neededAmount > inStockAmount -> {
                    // We don't have enough in stock
                    stock.remove(ingredient.name)
                    neededAmount -= inStockAmount
                }
                neededAmount <= inStockAmount -> {
                    // We have enough in stock so we don't need extra ores
                    stock[ingredient.name] = inStockAmount - neededAmount
                    return 0
                }
            }

            val recipe = findRecipe(ingredient.name) // Find the recipe to create the ingredient
            val recipeCreatesAmount =
                recipe.result.amount // The recipe creates this much of the ingredient for one execution
            val executeRecipeTimes = when {
                neededAmount % recipeCreatesAmount == 0L -> {
                    neededAmount / recipeCreatesAmount // We can create the exact amount needed
                }
                else -> {
                    neededAmount / recipeCreatesAmount + 1 // We have some ingredients left over
                }
            } // We need to execute the recipe this many times to get at least *neededAmount* of the ingredient
            val recipeTotalAmount =
                recipeCreatesAmount * executeRecipeTimes // The total amount of an ingredient we'll get
            if (recipeTotalAmount > neededAmount) {
                stock.merge(ingredient.name, recipeTotalAmount - neededAmount, ::sum) // Update the stock
            }

            return recipe.ingredients.map { getRequiredOre(Ingredient(it.name, it.amount * executeRecipeTimes)) }.sum()
        }

        val fuel = Ingredient("FUEL", 1)
        val requiredOre = getRequiredOre(fuel)
        yield(requiredOre) // Part 1

        val oreStock = 1000000000000L

        // Find a minimum & maximum
        var min = 0L
        var max = 1L
        while (getRequiredOre(fuel) < oreStock) {
            min = max
            max *= 2
            fuel.amount = max
        }

        // Narrow down between the minimum & maximum
        loop@ while (min + 1 < max) {
            val between = (min + max) / 2
            fuel.amount = between
            val required = getRequiredOre(fuel)
            when {
                required < oreStock -> min = between
                required > oreStock -> max = between
                required == oreStock -> {
                    min = between
                    break@loop
                }
            }
        }

        yield(min) // Part 2
    }

    class Recipe(val ingredients: List<Ingredient>, val result: Ingredient) {
        companion object {
            fun fromString(text: String): Recipe {
                val recipe = text.trim().split("=>")
                val ingredients = recipe[0]
                    .split(",")
                    .map { Ingredient.fromString(it) }
                val result = Ingredient.fromString(recipe[1])
                return Recipe(ingredients, result)
            }
        }

        override fun toString(): String {
            return "${ingredients.joinToString(", ")} => $result"
        }
    }

    class Ingredient(val name: String, var amount: Long) {
        companion object {
            fun fromString(text: String): Ingredient {
                val value = text.trim().split(" ")
                return Ingredient(value[1], value[0].toLong())
            }
        }

        override fun toString(): String {
            return "$amount $name"
        }
    }
}