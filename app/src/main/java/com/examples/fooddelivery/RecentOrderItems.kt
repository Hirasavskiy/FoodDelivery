package com.examples.fooddelivery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.fooddelivery.adapter.RecentBuyAdapter
import com.examples.fooddelivery.databinding.ActivityRecentOrderItemsBinding
import com.examples.fooddelivery.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {

    private val binding : ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }

    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }

        val recentOrderItems = intent.getSerializableExtra("RecentBuyOrderItem") as? ArrayList<OrderDetails>

        recentOrderItems?.let { orderDetails ->
            allFoodNames = ArrayList()
            allFoodImages = ArrayList()
            allFoodPrices = ArrayList()
            allFoodQuantities = ArrayList()

            for (orderDetail in orderDetails) {
                allFoodNames.addAll(orderDetail.foodNames ?: emptyList())
                allFoodImages.addAll(orderDetail.foodImages ?: emptyList())
                allFoodPrices.addAll(orderDetail.foodPrices ?: emptyList())
                allFoodQuantities.addAll(orderDetail.foodQuantities ?: emptyList())
            }
        }

        setAdapter()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setAdapter() {
        val rv = binding.recyclerViewRecentBuy
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this, allFoodNames, allFoodImages, allFoodPrices, allFoodQuantities)
        rv.adapter = adapter
    }
}
