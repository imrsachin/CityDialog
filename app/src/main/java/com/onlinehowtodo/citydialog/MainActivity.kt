package com.onlinehowtodo.citydialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.onlinehowtodo.citydialog.databinding.ActivityMainBinding
import com.onlinehowtodo.citydialog.databinding.AddCityDialogBinding

class MainActivity : AppCompatActivity() {
    private val cityData = mutableListOf<City>()
    private val CITY_KEY: String = "CITY_KEY"
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate
                    .MODE_NIGHT_NO);
        setContentView(binding.root)
        with(binding) {
            resetBtn.setOnClickListener {
                resetCityList()
            }
            addBtn.setOnClickListener {
                addCityData()
            }
        }
        adapter = CityAdapter{
            Toast.makeText(this@MainActivity, "${it.capital} click", Toast.LENGTH_SHORT).show()
        }
        val recyclerView = binding.cityRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        loadCities()
    }



    override fun onPause() {
        super.onPause()
        Log.d("activity", "onDestroy: ")
        saveCityData()
    }

    private fun addCityData() {
        val builder = AlertDialog.Builder(this)
        val view = AddCityDialogBinding.inflate(layoutInflater)
        with(builder) {
            setTitle("Add City")
            setMessage("Add city item")
            setView(view.root)
            setPositiveButton("Yes") { _, i ->
                val cityName = view.cityNameTxt.text.toString()
                val cityCapital = view.cityCapitalTxt.text.toString()
                val city = City(cityName, cityCapital)
                cityData.add(city)
                adapter.submitList(cityData.shuffled())
            }
            setNegativeButton("No") { _, i ->

            }
        }
        builder.create().show()
    }

    private fun resetCityList() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Reset city list")
            setMessage("Do you want to reset data?")
            setPositiveButton("Yes") { _, i ->
                cityData.clear()
                adapter.notifyDataSetChanged()
            }
            setNegativeButton("No") { _, i ->

            }
        }
        builder.create().show()
    }

    private fun saveCityData() {
        with(getPreferences(Context.MODE_PRIVATE).edit()) {
            val gson = Gson()
            val citySet = cityData.map {
                gson.toJson(it)
            }
            putStringSet(CITY_KEY, citySet.toSet())
            commit()
        }
    }

    private fun loadCities() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref) {
            val cities = getStringSet(CITY_KEY, null)?.toList()
            val gson = Gson()
            cities?.forEach {
                cityData.add(gson.fromJson(it, City::class.java))
            }
        }
        adapter.submitList(cityData)

    }
}