package com.example.condotracking.ui.feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import com.example.condotracking.R
import com.example.condotracking.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {

    private var binding: ActivityFeedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setupViews()
    }

    private fun setupViews(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFeed, FeedFragment())
            commit()
        }
    }
}