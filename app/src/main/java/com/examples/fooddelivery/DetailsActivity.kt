package com.examples.fooddelivery

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.examples.fooddelivery.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private var foodPrice: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foodName = intent.getStringExtra("ProductItemName")
        foodDescription = intent.getStringExtra("ProductItemDescription")
        foodIngredients = intent.getStringExtra("ProductItemIngredients")
        foodPrice = intent.getStringExtra("ProductItemPrice")
        foodImage = intent.getStringExtra("ProductItemImage")
        with(binding){
            detailFoodName.text = foodName
            descriptionText.text = foodDescription
            ingridientsText.text = foodIngredients
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
            detailFoodImage
        }
        binding.imageButton.setOnClickListener {
            finish()
        }
    }
}