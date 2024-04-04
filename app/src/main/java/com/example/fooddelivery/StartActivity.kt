package com.example.fooddelivery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.fooddelivery.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goUserLoginBtn.setOnClickListener{
            val intent = Intent(this@StartActivity, LoginUserActivity ::class.java)
            startActivity(intent)
            finish()
        }
    }
}