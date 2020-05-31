package com.chandigarhthecitybeautiful.adpaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chandigarhthecitybeautiful.databinding.PlaceItemViewBinding
import com.chandigarhthecitybeautiful.model.Place

class PlaceGridAdapter(private val places: List<Place>, val clickListener:PlaceGridAdapter.onClickListener) :
    RecyclerView.Adapter<PlaceGridAdapter.PlaceViewHolder>() {

    interface onClickListener{
        fun onClick(place: Place)
    }

    class PlaceViewHolder(private val binding: PlaceItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            binding.place = place
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceItemViewBinding.inflate(inflater, parent, false)
        return PlaceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.itemView.setOnClickListener(View.OnClickListener {
            clickListener.onClick(place)
        })
        holder.bind(place)
    }
}