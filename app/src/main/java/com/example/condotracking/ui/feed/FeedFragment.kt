import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.condotracking.databinding.FragmentFeedBinding
import com.example.condotracking.ui.feed.FeedViewModel
import com.example.condotracking.ui.feed.adapter.FilterAdapter
import com.example.condotracking.ui.feed.adapter.OfferAdapter
import com.example.condotracking.ui.feed.data.FilterItem
import com.example.condotracking.ui.homeFeed.HomeActivity
import com.google.firebase.analytics.FirebaseAnalytics

class FeedFragment : Fragment() {

    private var viewModel: FeedViewModel? = null
    private var binding: FragmentFeedBinding? = null
    private var recyclerOffer: RecyclerView? = null
    private var recyclerChipFilter: RecyclerView? = null
    private var filterAdapter: FilterAdapter = FilterAdapter()
    private var offerAdapter: OfferAdapter = OfferAdapter()
    private var toAnalytics: ImageView? = null
    private var currentCategoryStartTime: Long = 0
    private var lastSelectedFilter: FilterItem? = null

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]

        recyclerOffer = binding?.recyclerOffers
        recyclerChipFilter = binding?.recyclerChip
        toAnalytics = binding?.toAnalytics

        recyclerOffer?.adapter = offerAdapter
        recyclerChipFilter?.adapter = filterAdapter

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        setupBindings()
        setupViews()

        return binding?.root
    }

    private fun setupBindings() {
        viewModel?.filters?.observe(viewLifecycleOwner) { filters ->
            filterAdapter.setFilter(filters)
        }
        viewModel?.offers?.observe(viewLifecycleOwner) { offers ->
            offerAdapter.setOffers(offers)
        }
    }

    private fun setupViews() {
        filterAdapter.onChipClick = { selectedFilter ->
            viewModel?.onChipClicked(selectedFilter)

            if (selectedFilter != lastSelectedFilter) {
                leaveFilter(lastSelectedFilter?.name ?: "")
            }

            currentCategoryStartTime = System.currentTimeMillis()

            trackScreenView(selectedFilter.name)
            val filteredOffers = viewModel?.getFilteredOffers(selectedFilter.name) ?: emptyList()
            offerAdapter.setOffers(filteredOffers)

            lastSelectedFilter = selectedFilter
        }
        goToAnalytics()
    }

    private fun trackScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


    private fun leaveFilter(selectedFilterName: String) {
        if (currentCategoryStartTime > 0) {
            val timeSpentInMillis = System.currentTimeMillis() - currentCategoryStartTime
            val timeSpentInMinutes = timeSpentInMillis / (1000 * 60)

            val timeBundle = Bundle().apply {
                putLong("tempo_engajamento_em_minutos", timeSpentInMinutes)
                putString("filtro", selectedFilterName)
            }
            firebaseAnalytics.logEvent("tempo_engajamento_em_minutos", timeBundle)
        }
    }

    private fun goToAnalytics() {
        toAnalytics?.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }
    }
}

