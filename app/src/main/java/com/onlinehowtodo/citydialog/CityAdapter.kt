package com.onlinehowtodo.citydialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onlinehowtodo.citydialog.databinding.CityDetailsBinding


class CityAdapter(val listener: (City) -> Unit) :
    ListAdapter<City, CityAdapter.ViewHolder>(object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }) {

    inner class ViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val cityName:TextView = view.findViewById(R.id.city_name)
        private val cityCapital:TextView = view.findViewById(R.id.city_capital)

        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(layoutPosition))
            }
        }

        fun bind(city: City) {
            with(city) {
                cityName.text = name
                cityCapital.text = capital
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.city_details,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
