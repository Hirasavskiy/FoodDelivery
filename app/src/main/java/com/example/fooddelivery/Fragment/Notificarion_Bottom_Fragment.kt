package com.example.fooddelivery.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.adapter.NotificationAdapter
import com.example.fooddelivery.databinding.FragmentNotificarionBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Notificarion_Bottom_Fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificarionBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificarionBottomBinding.inflate(layoutInflater, container, false)
        val notifications = listOf("Ваш заказ был отменён", "Заказ забрал курьер", "Заказ доставлен!")
        val notificationImages = listOf(R.drawable.n_unsuccess, R.drawable.courier, R.drawable.ic_comleted)
        val adapter = NotificationAdapter(ArrayList(notifications), ArrayList(notificationImages))
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter =  adapter
        return binding.root
    }

    companion object {

    }
}