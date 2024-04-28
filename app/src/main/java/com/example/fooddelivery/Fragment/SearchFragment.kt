package com.example.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.adapter.MenuAdapter
import com.example.fooddelivery.databinding.FragmentSearchBinding
import android.widget.SearchView

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : MenuAdapter
    private val orignalMenuFoodName = listOf("Крабовые палочки", "Мороженое", "Пельмени")
    private val originalMenuItemPrice = listOf("2 р.", "2.50 р.", "3.20 р.")
    private val originalMenuImage = listOf(
        R.drawable.palochki,
        R.drawable.toparbuz,
        R.drawable.pelmenisochnye
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val filteredProductName = mutableListOf<String>()
    private val filteredProductPrice = mutableListOf<String>()
    private val filteredProductImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(filteredProductName, filteredProductPrice, filteredProductImage)
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = adapter

        setupSearchView()
        showAllProduct()

        return binding.root
    }

    private fun showAllProduct() {
        filteredProductName.clear()
        filteredProductPrice.clear()
        filteredProductImage.clear()
        filteredProductName.addAll(orignalMenuFoodName)
        filteredProductPrice.addAll(originalMenuItemPrice)
        filteredProductImage.addAll(originalMenuImage)

        adapter.notifyDataSetChanged()
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterProductItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterProductItems(newText)
                return true
            }
        })
    }

    private fun filterProductItems(query: String){
        filteredProductName.clear()
        filteredProductPrice.clear()
        filteredProductImage.clear()

        orignalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query.toString(), ignoreCase = true)){
                filteredProductName.add(foodName)
                filteredProductPrice.add(originalMenuItemPrice[index])
                filteredProductImage.add(originalMenuImage[index])
            }
        }
        adapter.notifyDataSetChanged()
    }
    companion object {
    }
}

