package com.examples.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.fooddelivery.adapter.MenuAdapter
import com.examples.fooddelivery.databinding.FragmentSearchBinding
import android.widget.SearchView
import com.examples.fooddelivery.model.ProductItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalProductItems = mutableListOf<ProductItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        adapter = MenuAdapter(emptyList(), requireContext()) // Инициализируем адаптер
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = adapter

        retrieveMenuItem()

        setupSearchView()
        return binding.root
    }


    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodReference: DatabaseReference = database.reference.child("product")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(ProductItem::class.java)
                    menuItem?.let {
                        originalProductItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showAllMenu() {
        val filteredProductItems = ArrayList(originalProductItems)
        setAdapter(filteredProductItems)
    }

    private fun setAdapter(filteredProductItems: List<ProductItem>) {
        adapter = MenuAdapter(filteredProductItems, requireContext())
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productRecyclerView.adapter = adapter
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
        val filteredProductItem = originalProductItems.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredProductItem)
    }
    companion object {
    }
}

