package com.examples.fooddelivery.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.fooddelivery.adapter.MenuAdapter
import com.examples.fooddelivery.databinding.FragmentMenuBotomSheetBinding
import com.examples.fooddelivery.model.ProductItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuBotomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var binding:FragmentMenuBotomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var productItems: MutableList<ProductItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBotomSheetBinding.inflate(inflater, container, false)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        retrieveProductItems()

        return binding.root
    }

    private fun retrieveProductItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("product")
        productItems = mutableListOf()
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val productItem = foodSnapshot.getValue(ProductItem::class.java)
                    productItem?.let { productItems.add(it) }
                }
                Log.d("ITEMS", "onDataChange: Data recieved")
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setAdapter() {
        if (productItems.isNotEmpty()){
            val adapter = MenuAdapter(productItems, requireContext())
            binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecyclerView.adapter = adapter
            Log.d("ITEMS", "setAdapter: data set")
        }
        else{
            Log.d("ITEMS", "setAdapter: data NOT set")
        }

    }
    companion object {

    }
}