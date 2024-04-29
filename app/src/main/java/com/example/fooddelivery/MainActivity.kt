package com.example.fooddelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fooddelivery.Fragment.CartFragment
import com.example.fooddelivery.Fragment.HistoryFragment
import com.example.fooddelivery.Fragment.HomeFragment
import com.example.fooddelivery.Fragment.Notificarion_Bottom_Fragment
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
        changefragment(HomeFragment())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.homeFragment -> {
                    changefragment(HomeFragment())
                    true
                }
                R.id.cartFragment -> {
                    changefragment(CartFragment())
                    true
                }
                R.id.historyFragment -> {
                    changefragment(HistoryFragment())
                    true
                }
                R.id.profileFragment -> {
                    changefragment(ProfileFragment())
                    true
                }
                R.id.searchFragment -> {
                    changefragment(SearchFragment())
                    true
                }
                else -> false
            }
        }
        binding.notificationsButton.setOnClickListener{
            val bottomSheetDialog = Notificarion_Bottom_Fragment()
            bottomSheetDialog.show(supportFragmentManager, "Test")
        }
    }

    fun changefragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTranasction = fragmentManager.beginTransaction()
        fragmentTranasction.replace(R.id.fragment_container, fragment)
        fragmentTranasction.addToBackStack(null) // Добавьте это, чтобы обеспечить возможность возврата к предыдущему фрагменту
        fragmentTranasction.commit()
    }

}
