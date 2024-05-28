package com.examples.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.examples.fooddelivery.Fragment.CartFragment
import com.examples.fooddelivery.Fragment.HistoryFragment
import com.examples.fooddelivery.Fragment.HomeFragment
import com.examples.fooddelivery.Fragment.Notificarion_Bottom_Fragment
import com.examples.fooddelivery.Fragment.ProfileFragment
import com.examples.fooddelivery.Fragment.SearchFragment
import com.examples.fooddelivery.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var nameOfShop: String
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
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
        binding.backBtn.setOnClickListener{
            //val bottomSheetDialog = Notificarion_Bottom_Fragment()
            //bottomSheetDialog.show(supportFragmentManager, "Test")
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            startActivity(Intent(this, SignUpUserActivity::class.java))
            finish()
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
