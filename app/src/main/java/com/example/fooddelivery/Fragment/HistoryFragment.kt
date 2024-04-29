package com.example.fooddelivery.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.adapter.BuyAgainAdapter
import com.example.fooddelivery.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()
        return binding.root
    }
    private fun setUpRecyclerView(){
        val buyAgainFoodName = arrayListOf("Палочки", "Пельмени", "Мороженое")
        val buyAgainFoodPrice = arrayListOf("2.00 р.", "3.50 р.", "2.50 р.")
        val buyAgainFoodImage = arrayListOf(R.drawable.palochki, R.drawable.pelmenisochnye, R.drawable.toparbuz)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage)
        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    companion object {


    }
}