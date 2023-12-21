package com.example.edupath.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.edupath.R
import com.example.edupath.databinding.ActivityMainBinding
import com.example.edupath.view.main.community.CommunityFragment
import com.example.edupath.view.main.home.HomeFragment
import com.example.edupath.view.main.profile.ProfileFragment
import com.example.edupath.view.main.progress.ProgressFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.community -> replaceFragment(CommunityFragment())
                R.id.progress -> replaceFragment(ProgressFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else ->{

                }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}