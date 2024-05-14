package com.examples.fooddelivery.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examples.fooddelivery.databinding.BuyItemAgainBinding


class BuyAgainAdapter(private val buyAgainProductName:MutableList<String>,
                      private val buyAgainProductPrice: MutableList<String>,
                      private val buyAgainProductImage: MutableList<String>,
                      private var requireContext: Context):
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(
            buyAgainProductName[position],
            buyAgainProductPrice[position],
            buyAgainProductImage[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyItemAgainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainProductName.size

    inner class BuyAgainViewHolder(private val binding: BuyItemAgainBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(foodName: String, foodPrice: String, foodImage: String) {
            binding.buyAgainFoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
            val uriString = foodImage
            val uri = Uri.parse(uriString)
            Log.d("BuyAgainAdapter", "buyAgainProductName size: ${buyAgainProductName.size}")
            Log.d("BuyAgainAdapter", "buyAgainProductPrice size: ${buyAgainProductPrice.size}")
            Log.d("BuyAgainAdapter", "buyAgainProductImage size: ${buyAgainProductImage.size}")
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)
        }

    }
}