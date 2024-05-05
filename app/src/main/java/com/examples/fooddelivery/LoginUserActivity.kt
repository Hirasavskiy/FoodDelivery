package com.examples.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.examples.fooddelivery.databinding.ActivityLoginUserBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityLoginUserBinding
class LoginUserActivity : AppCompatActivity() {
    private var userName: String ?= null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        binding.goSingUpUser.setOnClickListener(){
            val intent = Intent(this@LoginUserActivity, SignUpUserActivity ::class.java)
            startActivity(intent)
        }
        binding.signInAccountBtn.setOnClickListener {


            email = binding.signInEmail.text.toString().trim()
            password = binding.signInPassword.text.toString().trim()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show()
            }
            else{
                createUser()
                Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateUi(user)
            }
            else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        updateUi(user)
                    }
                    else{
                        Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@LoginUserActivity, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        val intent = Intent(this@LoginUserActivity, MainActivity :: class.java)
        startActivity(intent)
        finish()
    }
}