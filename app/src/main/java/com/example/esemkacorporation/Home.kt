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

        val fragmentList = listOf(ProfileFragment(), JobMutationFragment(), JobPromotionFragment())
        val nameList = listOf("Profile", "Job Mutation", "Job Promotion")



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