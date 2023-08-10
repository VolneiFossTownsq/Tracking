package com.example.condotracking.ui.homeFeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.condotracking.databinding.FragmentHomeBinding
import com.example.condotracking.ui.feed.FeedActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class HomeFragment : Fragment() {

    companion object {
        private const val ARG_FILTER_ID = "filterId"

        fun newInstance(filterId: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_FILTER_ID, filterId)
            fragment.arguments = args
            return fragment
        }
    }


    private var binding: FragmentHomeBinding? = null
    private var initButton: Button? = null
    private var timeTextView: TextView? = null
    private var selectedCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initButton = binding?.init
        timeTextView = binding?.textView

        selectedCategory = arguments?.getString(ARG_FILTER_ID)

        setupViews()

        return binding?.root
    }

    private fun setupViews() {
        initButton?.setOnClickListener {
            val intent = Intent(requireContext(), FeedActivity::class.java)
            startActivity(intent)
        }

        if (!selectedCategory.isNullOrEmpty()) {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)

            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val filterTimeInMinutes = remoteConfig.getLong("tempo_gasto_em_minutos")

                        val timeText =
                            "O tempo que você passou na categoria $selectedCategory: $filterTimeInMinutes minutos"
                        timeTextView?.text = timeText
                    } else {
                        // Tratar erros aqui
                    }
                }
        } else {
            timeTextView?.visibility = View.GONE
        }
    }
}

