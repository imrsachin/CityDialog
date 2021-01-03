package com.onlinehowtodo.citydialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.onlinehowtodo.citydialog.databinding.CityDetailsBinding

class CityAdapter(val cityData: MutableList<City>) : BaseAdapter() {
    override fun getCount(): Int {
        return cityData.size
    }

    override fun getItem(position: Int): Any {
        return cityData[position]
    }

    override fun getItemId(position: Int): Long {
        return cityData[position].name.hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val binding = CityDetailsBinding.inflate(LayoutInflater.from(parent!!.context))
            view = binding.root
            viewHolder = ViewHolder(binding)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }
        with(viewHolder) {
            cityName.text = cityData[position].name
            cityCapital.text = cityData[position].capital
        }
        return view

    }

    class ViewHolder(val binding: CityDetailsBinding) {
        val cityName = binding.cityName
        val cityCapital = binding.cityCapital
    }
}