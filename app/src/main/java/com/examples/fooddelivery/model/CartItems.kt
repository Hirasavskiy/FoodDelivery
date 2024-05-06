package com.examples.fooddelivery.model

data class CartItems(
    var foodName: String? = null,
    var foodPrice: String? = null,
    var foodDescription: String? = null,
    var foodImage: String? = null,
    var foodQuantity: Int? = null,
    var foodIngredient: String? = null
)
