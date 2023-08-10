package com.example.condotracking.ui.homeFeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.condotracking.R
import com.example.condotracking.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupViews()
    }

    private fun setupViews(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentHome, HomeFragment())
            commit()
        }
    }
}