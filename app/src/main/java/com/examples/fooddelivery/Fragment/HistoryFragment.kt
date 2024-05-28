package com.examples.fooddelivery.Fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.examples.fooddelivery.adapter.BuyAgainAdapter
import com.examples.fooddelivery.databinding.FragmentHistoryBinding
import com.examples.fooddelivery.model.OrderDetails
import com.examples.fooddelivery.RecentOrderItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        retrieveBuyHistory()


        binding.recentBuyItem.setOnClickListener {
            seeItemsRecentBuy()
        }

        binding.receivedButton.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompleteOrder").child(itemPushKey!!)
        completeOrderReference.child("paymentRecieved").setValue(true)
    }

    private fun seeItemsRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), RecentOrderItems::class.java)
            intent.putExtra("RecentBuyOrderItem", ArrayList(listOfOrderItem))
            startActivity(intent)
        }
    }

    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""

        val buyItemReference: DatabaseReference =
            database.reference.child("customer").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children) {
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        // Добавляем каждый элемент из заказа в список отдельно
                        val foodNames = it.foodNames ?: mutableListOf()
                        val foodPrices = it.foodPrices ?: mutableListOf()
                        val foodImages = it.foodImages ?: mutableListOf()
                        val foodQuantities = it.foodQuantities ?: mutableListOf()

                        for (index in foodNames.indices) {
                            val orderItem = it.userUid?.let { it1 ->
                                it.userName?.let { it2 ->
                                    it.address?.let { it3 ->
                                        it.totalPrice?.let { it4 ->
                                            it.phoneNumber?.let { it5 ->
                                                it.orderAccepted?.let { it6 ->
                                                    it.paymentReceived?.let { it7 ->
                                                        OrderDetails(
                                                            it1,
                                                            it2,
                                                            ArrayList(mutableListOf(foodNames[index])),
                                                            ArrayList(mutableListOf(foodPrices[index])),
                                                            ArrayList(mutableListOf(foodImages[index])),
                                                            ArrayList(mutableListOf(foodQuantities[index])),
                                                            it3,
                                                            it4,
                                                            it5,
                                                            it.currentTime,
                                                            it.itemPushKey,
                                                            it6,
                                                            it7
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (orderItem != null) {
                                listOfOrderItem.add(orderItem)
                            }
                        }
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()) {
                    setDataInRecentBuyItem()
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HistoryFragment", "Database error: $error")
            }
        })
    }


    private fun setDataInRecentBuyItem() {
        binding.recentBuyItem.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding) {
                buyAgainName.text = it.foodNames?.firstOrNull() ?: ""
                buyAgainFoodPrice.text = it.foodPrices?.firstOrNull() ?: ""
                val image = it.foodImages?.firstOrNull() ?: ""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)

                val isOrderAccepted = listOfOrderItem[0].orderAccepted
                if (isOrderAccepted) {
                    orderStatus.background.setTint(Color.GREEN)
                    receivedButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrice = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()

        // Заполнение списков buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage
        for (orderItem in listOfOrderItem) {
            orderItem.foodNames?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                orderItem.foodPrices?.firstOrNull()?.let {
                    buyAgainFoodPrice.add(it)
                    orderItem.foodImages?.firstOrNull()?.let {
                        buyAgainFoodImage.add(it)
                    }
                }
            }
        }

        // Создание адаптера и установка его для RecyclerView
        val rv = binding.BuyAgainRecyclerView
        rv.layoutManager = LinearLayoutManager(requireContext())
        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodPrice,
            buyAgainFoodImage,
            requireContext()
        )
        Log.d("HistoryFragment", "buyAgainFoodName size: ${buyAgainFoodName.size}")
        Log.d("HistoryFragment", "buyAgainFoodPrice size: ${buyAgainFoodPrice.size}")
        Log.d("HistoryFragment", "buyAgainFoodImage size: ${buyAgainFoodImage.size}")

        rv.adapter = buyAgainAdapter

        // Уведомление адаптера о том, что данные изменились
        buyAgainAdapter.notifyDataSetChanged()
        
    }


}