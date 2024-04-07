package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.databinding.ActivityLoginUserBinding

private lateinit var binding: ActivityLoginUserBinding
class LoginUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goSingUpUser.setOnClickListener(){
            val intent = Intent(this@LoginUserActivity, SignUpUserActivity ::class.java)
            startActivity(intent)
        }
    }
}