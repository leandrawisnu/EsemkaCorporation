package com.example.esemkacorporation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkacorporation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // DateTime
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        // End

        // Fragment Profile
        val hello : TextView = findViewById(R.id.hello_employee)
        val name : TextView = findViewById(R.id.profile_name)
        val email : TextView = findViewById(R.id.profile_email)
        val phone : TextView = findViewById(R.id.profile_number)
        val hiredate : TextView = findViewById(R.id.profile_hiredate)
        val position : TextView = findViewById(R.id.profile_position)
        val joblevel : TextView = findViewById(R.id.profile_joblevel)
        val departement : TextView = findViewById(R.id.profile_departement)
        // End

        val id = getSharedPreferences("EsemkaCorporation", MODE_PRIVATE).getInt("ID", 0)
        val fragmentList = listOf(ProfileFragment(), JobMutationFragment(), JobPromotionFragment())
        val nameList = listOf("Profile", "Job Mutation", "Job Promotion")

        GlobalScope.launch(Dispatchers.IO) {
            val conn = URL("https://3967-27-131-249-215.ngrok-free.app/api/Employee/${id}").openConnection() as HttpURLConnection
            val response = conn.inputStream.bufferedReader().readText()
            val data = JSONObject(response)
            val rawDate = data.getString("hireDate")
            val parsedDateTime = LocalDateTime.parse(rawDate)
            val parsedDate = parsedDateTime.toLocalDate()

            GlobalScope.launch(Dispatchers.Main) {
                hello.text = "Hello, ${data.getString("name")}"
                name.text = data.getString("name")
                email.text = data.getString("email")
                phone.text = data.getString("phoneNumber")
                hiredate.text = parsedDate.format(formatter)
            }
        }

        binding.vpMenu.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

        }
        TabLayoutMediator(binding.tpMenu, binding.vpMenu) { tab, position->
            tab.text = nameList[position]
        }.attach()
    }
}