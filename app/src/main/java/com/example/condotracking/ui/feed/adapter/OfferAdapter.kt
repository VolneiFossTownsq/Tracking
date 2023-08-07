package com.example.condotracking.ui.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.condotracking.R
import com.example.condotracking.ui.feed.data.FilterItem
import com.example.condotracking.ui.feed.data.Offer

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    private var offersItens = listOf<Offer>()

    fun setOffers(offers: List<Offer>) {
        offersItens = offers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.offer_item, parent, false)
        return OfferViewHolder(itemView)
    }

    override fun getItemCount(): Int = offersItens.size

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val item = offersItens[position]
        holder.bind(item)
    }

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var titleOffer: TextView = itemView.findViewById(R.id.nameOffer)
        private var offerDescription: TextView = itemView.findViewById(R.id.cardDescription)
        private var icOffer: ImageView = itemView.findViewById(R.id.icOffer)

        fun bind(item: Offer) {

            icOffer.setImageResource(R.drawable.ic_launcher_background)
            titleOffer.text = item.title
            offerDescription.text = item.description
        }
    }

}