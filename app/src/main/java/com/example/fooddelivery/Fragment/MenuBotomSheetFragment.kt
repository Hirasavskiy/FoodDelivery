package com.example.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.adapter.MenuAdapter
import com.example.fooddelivery.databinding.FragmentMenuBotomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBotomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var binding:FragmentMenuBotomSheetBinding

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

        val menuFoodName = listOf("Крабовые палочки", "Мороженое", "Пельмени")
        val menuItemPrice = listOf("2 р.", "2.50 р.", "3.20 р.")
        val menuImage = listOf(
            R.drawable.palochki,
            R.drawable.toparbuz,
            R.drawable.pelmenisochnye
        )
        val adapter = MenuAdapter(ArrayList(menuFoodName), ArrayList(menuItemPrice), ArrayList(menuImage), requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}