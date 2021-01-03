package com.onlinehowtodo.citydialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.onlinehowtodo.citydialog.databinding.ActivityMainBinding
import com.onlinehowtodo.citydialog.databinding.AddCityDialogBinding

class MainActivity : AppCompatActivity() {
    val cityData = mutableListOf<City>()
    val CITY_KEY: String = "CITY_KEY"
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadCities()
        with(binding) {
            resetBtn.setOnClickListener {
                resetCityList()
            }
            addBtn.setOnClickListener {
                addCityData()
            }
        }
        adapter = CityAdapter(cityData)
        binding.cityListView.adapter = adapter


    }

    override fun onDestroy() {
        super.onDestroy()
        saveCityData()
    }

    fun addCityData() {
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
                adapter.notifyDataSetChanged()
            }
            setNegativeButton("No") { _, i ->

            }
        }
        builder.create().show()
    }

    fun resetCityList() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Reset city list")
            setMessage("Do you want to reset data?")
            setPositiveButton("Yes") { _, i ->
                cityData.clear()
                saveCityData()
                adapter.notifyDataSetChanged()
            }
            setNegativeButton("No") { _, i ->

            }
        }
        builder.create().show()
    }

    fun saveCityData() {
        with(getPreferences(Context.MODE_PRIVATE).edit()) {
            val gson = Gson()
            val citySet = cityData.map {
                gson.toJson(it)
            }
            putStringSet(CITY_KEY, citySet.toSet())
            commit()
        }
    }

    fun loadCities() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref) {
            val cities = getStringSet(CITY_KEY, null)?.toList()
            val gson = Gson()
            cities?.forEach {
                cityData.add(gson.fromJson(it, City::class.java))
            }
        }

    }
}