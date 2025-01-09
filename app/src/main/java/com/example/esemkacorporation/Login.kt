package com.example.esemkacorporation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.esemkacorporation.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val home = Intent(this, Home::class.java)

        binding.loginBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                // URL API menyesuaikan (Disini pakai ngrok)
                val conn = URL("https://3967-27-131-249-215.ngrok-free.app/api/Employee/Login").openConnection() as HttpURLConnection
                conn.setRequestProperty("Content-Type", "application/json")
                conn.requestMethod = "POST"

                val data = JSONObject()
                data.put("email", binding.emailEtLogin.text)
                data.put("password", binding.passwordEtLogin.text)

                conn.outputStream.write(data.toString().toByteArray())
                val id = conn.inputStream.bufferedReader().readText().toInt()
                val responseCode = conn.responseCode
                GlobalScope.launch(Dispatchers.Main) {
                    if (responseCode in 200..201) {
                        getSharedPreferences("EsemkaCorporation", MODE_PRIVATE).edit().putInt("ID", id).apply()
                        startActivity(home)
                    } else {
                        Toast.makeText(this@Login, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}