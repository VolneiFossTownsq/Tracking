package com.example.condotracking.ui.homeFeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.condotracking.databinding.FragmentHomeBinding
import com.example.condotracking.ui.feed.FeedActivity
import com.example.condotracking.ui.feed.FeedFragment


class HomeFragment : Fragment() {

    companion object {
        private const val ARG_FILTER_ID = "filterId"

        fun newInstance(filterId: String, id: Int): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_FILTER_ID, filterId)
            fragment.arguments = args
            return fragment
        }
    }

    private var binding: FragmentHomeBinding? = null
    private var initButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initButton = binding?.init

        setupViews()

        return binding?.root
    }

    private fun setupViews(){
        initButton?.setOnClickListener {
            val intent = Intent(requireContext(), FeedActivity::class.java)
            startActivity(intent)
        }
    }
}