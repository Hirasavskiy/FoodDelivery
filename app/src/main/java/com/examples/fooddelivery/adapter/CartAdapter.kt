package com.examples.fooddelivery.adapter

import android.app.DownloadManager.Request
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.examples.fooddelivery.databinding.CartItemBinding
import com.google.android.play.integrity.internal.i
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(private val context: Context,private val cartItems:MutableList<String>, private val cartItemPrices:MutableList<String>,
                  private var cartDescriptions: MutableList<String>, private var cartImages:MutableList<String>,
                  private val cartQuantities:MutableList<Int>, private var cartIngredients: MutableList<String>):
    RecyclerView.Adapter<CartAdapter.CartViewHolder> () {

        private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartItemNumber = cartItems.size

        itemQuantities = IntArray(cartItemNumber){1}
        cartItemsReference = database.reference.child("customer").child(userId).child("CartItems")
    }

    companion object{
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItems.size
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantities)
        return itemQuantity
    }

    inner class CartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = cartItems[position]
                cartItemQuantity.text = quantity.toString()
                cartItemPrice.text = cartItemPrices[position]
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)
                minusButton.setOnClickListener{
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener{
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener{
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(position)
                    }
                }

            }

        }
        private fun decreaseQuantity(position: Int){
            if(itemQuantities[position] > 1){
                itemQuantities[position]--
                cartQuantities[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity(position: Int){
            if(itemQuantities[position] < 20){
                itemQuantities[position]++
                cartQuantities[position] = itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
            val postionRetrieve = position
            getUniqueKeyAtPostion(postionRetrieve){uniqueKey ->
                if(uniqueKey !=null){
                    removeItem(position, uniqueKey)
                }
            }
        }

    }

    private fun removeItem(position: Int, uniqueKey: String) {
        if(uniqueKey != null){
            cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                cartItems.removeAt(position)
                cartImages.removeAt(position)
                cartDescriptions.removeAt(position)
                cartQuantities.removeAt(position)
                cartItemPrices.removeAt(position)
                //cartIngredients.removeAt(position)
                Toast.makeText(context, "Успешно удалено", Toast.LENGTH_SHORT).show()

                //обновление itemQouantities
                itemQuantities = itemQuantities.filterIndexed { index, i ->  index!= position}.toIntArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size)
            }.addOnFailureListener{
                Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUniqueKeyAtPostion(postionRetrieve: Int, onComplete: (String?) -> Unit) {
        cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey: String?= null
                snapshot.children.forEachIndexed{index, dataSnapshot ->
                    if (index == postionRetrieve){
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                }
                onComplete(uniqueKey)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}