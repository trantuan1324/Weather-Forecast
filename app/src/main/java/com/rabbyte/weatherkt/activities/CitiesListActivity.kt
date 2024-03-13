package com.rabbyte.weatherkt.activities

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rabbyte.weatherkt.R
import com.rabbyte.weatherkt.adapter.CitiesListAdapter
import com.rabbyte.weatherkt.databinding.ActivityCitiesListBinding
import com.rabbyte.weatherkt.model.CityResponseApi
import com.rabbyte.weatherkt.view_model.CityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitiesListBinding
    private val cityAdapter by lazy { CitiesListAdapter() }
    private val cityViewModel: CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitiesListBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            cityEdt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    progressBarCitiesList.visibility = View.VISIBLE
                    cityViewModel.loadCity(s.toString(), 10).enqueue(
                        object : Callback<CityResponseApi> {
                            override fun onResponse(
                                call: Call<CityResponseApi>,
                                response: Response<CityResponseApi>
                            ) {
                                if (response.isSuccessful) {
                                    val data = response.body()
                                    data?.let {
                                        progressBarCitiesList.visibility = View.GONE
                                        cityAdapter.differ.submitList(it)
                                        cityView.apply {
                                            layoutManager = LinearLayoutManager(
                                                this@CitiesListActivity,
                                                LinearLayoutManager.HORIZONTAL,
                                                false
                                            )
                                            adapter = cityAdapter
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<CityResponseApi>, t: Throwable) {
                                Toast.makeText(
                                    this@CitiesListActivity,
                                    "Không tìm thấy",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }

            })
        }
    }
}