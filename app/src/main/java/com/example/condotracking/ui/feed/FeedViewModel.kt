package com.example.condotracking.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.condotracking.R
import com.example.condotracking.ui.feed.data.FilterItem
import com.example.condotracking.ui.feed.data.Offer

class FeedViewModel : ViewModel() {

    private val _filters = MutableLiveData<List<FilterItem>>()
    val filters: LiveData<List<FilterItem>> = _filters

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private val _filteredOffers = MutableLiveData<List<Offer>>()
    val filteredOffers: LiveData<List<Offer>> = _filteredOffers

    init {
        val newFilters = listOf(
            FilterItem(1, "Alcohol", false),
            FilterItem(2, "Health", false),
            FilterItem(3, "Education", false)
        )

        val offerList = listOf(
            Offer(R.drawable.ic_launcher_background, "Offer - Alcohol", "Description 1"),
            Offer(R.drawable.ic_launcher_background, "Offer - Health", "Description 2"),
            Offer(R.drawable.ic_launcher_background, "Offer - Education", "Description 3"),
            Offer(R.drawable.ic_launcher_background, "Offer - Alcohol", "Description 4"),
            Offer(R.drawable.ic_launcher_background, "Offer - Health", "Description 5")
        )

        _filters.value = newFilters
        _offers.value = offerList
    }

    fun onChipClicked(filterItem: FilterItem) {
        val currentList = filters.value.orEmpty()
        val newList = currentList.map { item ->
            if (item.id == filterItem.id) {
                item.copy(isSelected = true)
            } else {
                item.copy(isSelected = false)
            }
        }
        _filters.value = newList
    }

    fun getFilteredOffers(filterTitle: String): List<Offer> {
        return offers.value?.filter { offer ->
            offer.title.contains(filterTitle, ignoreCase = true)
        } ?: emptyList()
    }
}
