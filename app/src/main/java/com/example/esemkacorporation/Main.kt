package com.example.esemkacorporation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkacorporation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

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