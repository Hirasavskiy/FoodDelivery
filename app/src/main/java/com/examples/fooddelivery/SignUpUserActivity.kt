package com.examples.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.examples.fooddelivery.databinding.ActivitySignUpUserBinding
import com.examples.fooddelivery.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlin.math.log

class SignUpUserActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivitySignUpUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database.reference
        binding = ActivitySignUpUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goLogUser.setOnClickListener {
            val intent = Intent(this@SignUpUserActivity, LoginUserActivity::class.java)
            startActivity(intent)
        }

        binding.createAccountBtn.setOnClickListener {
            username = binding.signUpUsername.text.toString()
            email = binding.signUpEmail.text.toString().trim()
            password = binding.signUpPassword.text.toString().trim()

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        Log.d("Account", "Attempting to create account with email: $email")
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Account", "createUserWithEmail: success")
                Toast.makeText(this, "Аккаунт создан успешно", Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.w("Account", "createUserWithEmail: failure", task.exception)
                Toast.makeText(this, "Ошибка создания", Toast.LENGTH_SHORT).show()
                task.exception?.let {
                    Log.e("AccountError", it.message ?: "Unknown error")
                }
            }
        }
    }

    private fun saveUserData() {
        username = binding.signUpUsername.text.toString()
        email = binding.signUpEmail.text.toString().trim()
        password = binding.signUpPassword.text.toString().trim()

        val user = UserModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            database.child("customer").child(userId).setValue(user)
                .addOnSuccessListener {
                    Log.d("Database", "User data saved successfully")
                }
                .addOnFailureListener {
                    Log.e("Database", "Failed to save user data", it)
                }
        } else {
            Log.e("Database", "User ID is null, cannot save user data")
        }
    }
}