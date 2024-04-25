package com.example.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fooddelivery.R
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
        return binding.root
    }

    companion object {

    }
}