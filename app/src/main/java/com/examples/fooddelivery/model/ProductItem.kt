package com.examples.fooddelivery.model

data class ProductItem(
    val foodName: String ?= null,
    val foodPrice: String ?= null,
    val foodDescription: String ?= null,
    val foodImage: String ?= null,
    val foodIngredient: String ?= null
)
