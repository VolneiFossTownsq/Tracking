import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.condotracking.databinding.FragmentFeedBinding
import com.example.condotracking.ui.feed.FeedViewModel
import com.example.condotracking.ui.feed.adapter.FilterAdapter
import com.example.condotracking.ui.feed.adapter.OfferAdapter
import com.example.condotracking.ui.feed.data.FilterItem

class FeedFragment : Fragment() {

    private var viewModel: FeedViewModel? = null
    private var binding: FragmentFeedBinding? = null
    private var recyclerOffer: RecyclerView? = null
    private var recyclerChipFilter: RecyclerView? = null
    private var filterAdapter: FilterAdapter = FilterAdapter()
    private var offerAdapter: OfferAdapter = OfferAdapter()
    private var currentCategoryStartTime: Long = 0
    private var lastSelectedFilter: FilterItem? = null
    private var handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]

        recyclerOffer = binding?.recyclerOffers
        recyclerChipFilter = binding?.recyclerChip

        recyclerOffer?.adapter = offerAdapter
        recyclerChipFilter?.adapter = filterAdapter

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

            val filteredOffers = viewModel?.getFilteredOffers(selectedFilter.name) ?: emptyList()
            offerAdapter.setOffers(filteredOffers)

            lastSelectedFilter = selectedFilter
        }
    }

    private fun leaveFilter(selectedFilterName: String) {
        if (currentCategoryStartTime > 0) {
            val timeSpentInMillis = System.currentTimeMillis() - currentCategoryStartTime

            val timeSpentInSeconds = timeSpentInMillis / 1000
            val timeSpentInMinutes = timeSpentInSeconds / 60

            val timeSpentMessage = if (timeSpentInSeconds > 60) {
                "Tempo gasto no filtro $selectedFilterName: $timeSpentInMinutes minutos"
            } else {
                "Tempo gasto no filtro $selectedFilterName: $timeSpentInSeconds segundos"
            }

            handler.post {
                Toast.makeText(requireContext(), timeSpentMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
