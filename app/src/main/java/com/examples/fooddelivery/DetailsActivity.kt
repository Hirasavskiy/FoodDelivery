package com.examples.fooddelivery

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.examples.fooddelivery.databinding.ActivityDetailsBinding
import com.examples.fooddelivery.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foodName: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private var foodPrice: String? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
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
        binding.addToCartButton.setOnClickListener {
            addItemToCart()
        }
    }

    //создать cartItem объект
    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""

        val cartItem = CartItems(foodName.toString(), foodPrice.toString(), foodDescription.toString(), foodImage.toString(), 1)
        //сохранить данные в firebase
        database.child("customer").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "Успешно добавленно в корзину", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Ошибка добавления в корзину", Toast.LENGTH_SHORT).show()
        }
    }
}