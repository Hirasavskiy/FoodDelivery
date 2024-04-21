package com.example.fooddelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fooddelivery.Fragment.CartFragment
import com.example.fooddelivery.Fragment.HistoryFragment
import com.example.fooddelivery.Fragment.HomeFragment
import com.example.fooddelivery.Fragment.ProfileFragment
import com.example.fooddelivery.Fragment.SearchFragment
import com.example.fooddelivery.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnClickListener{
            when(it.id){
                R.id.homeFragment -> {
                    changefragment(HomeFragment())
                }
                R.id.cartFragment -> {
                    changefragment(CartFragment())
                }
                R.id.historyFragment -> {
                    changefragment(HistoryFragment())
                }
                R.id.profileFragment -> {
                    changefragment(ProfileFragment())
                }
                R.id.searchFragment -> {
                    changefragment(SearchFragment())
                }
            }
        }
    }
    fun changefragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTranasction = fragmentManager.beginTransaction()
        fragmentTranasction.replace(R.id.fragment_container, fragment)
        fragmentTranasction.commit()
    }
}