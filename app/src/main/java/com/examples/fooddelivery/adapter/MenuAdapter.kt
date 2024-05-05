package com.examples.fooddelivery.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examples.fooddelivery.DetailsActivity
import com.examples.fooddelivery.databinding.MenuItemBinding
import com.examples.fooddelivery.model.ProductItem

class MenuAdapter(
    private val productItems: List<ProductItem>,
    private val requireContext: Context)
    :RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = productItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val productItem = productItems[position]
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("ProductItemName", productItem.foodName)
                putExtra("ProductItemImage", productItem.foodImage)
                putExtra("ProductItemDescription", productItem.foodDescription)
                putExtra("ProductItemIngredients", productItem.foodIngredient)
                putExtra("ProductItemPrice", productItem.foodPrice)
            }
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val productItem = productItems[position]
            binding.apply {
                productFoodName.text = productItem.foodName
                productPrice.text = productItem.foodPrice
                val uri = Uri.parse(productItem.foodImage)
                Glide.with(requireContext).load(uri).into(productImage)
            }
        }

    }

}


