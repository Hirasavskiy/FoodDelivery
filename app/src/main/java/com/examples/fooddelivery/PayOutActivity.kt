package com.examples.fooddelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.examples.fooddelivery.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.PlaceOrder.setOnClickListener {
            val botomSheetDialog = CongratsBottomSheet()
            botomSheetDialog.show(supportFragmentManager, "Test")
        }
    }
}