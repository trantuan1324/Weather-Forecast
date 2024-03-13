package com.rabbyte.weatherkt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabbyte.weatherkt.databinding.ForecastViewholderBinding
import com.rabbyte.weatherkt.model.ForecastResponseApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private lateinit var binding: ForecastViewholderBinding
    private val differCallBack = object : DiffUtil.ItemCallback<ForecastResponseApi.data>() {
        override fun areItemsTheSame(
            oldItem: ForecastResponseApi.data,
            newItem: ForecastResponseApi.data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ForecastResponseApi.data,
            newItem: ForecastResponseApi.data
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ForecastViewholderBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ForecastViewholderBinding.bind(holder.itemView)
        val date = SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss",
            Locale.ENGLISH
        ).parse(differ.currentList[position].dtTxt.toString())
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "Sun"
            2 -> "Mon"
            3 -> "Tue"
            4 -> "Wed"
            5 -> "Thu"
            6 -> "Fri"
            7 -> "Sat"
            else -> "-"
        }
        binding.nameDayTxt.text = dayOfWeek
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val amOrPm = if (hour < 12) "am" else "pm"
        val hour12 = calendar.get(Calendar.HOUR)

        binding.hourTxt.text = buildString {
            append(hour12)
            append(" ")
            append(amOrPm)
        }

        binding.tempTxt.text = buildString {
            append(differ.currentList[position].main?.temp?.let {
                Math.round(it)
            }.toString())
            append("°")
        }

        val icon = when (differ.currentList[position].weather?.get(0)?.icon.toString()) {
            "01d", "0n" -> "sunny"
            "02d", "02n" -> "cloudy_sunny"
            "03d", "03n" -> "cloudy_sunny"
            "04d", "04n" -> "cloudy"
            "09d", "09n" -> "rainy"
            "10d", "10n" -> "rainy"
            "11d", "11n" -> "storm"
            "13d", "13n" -> "snowy"
            "50d", "50n" -> "windy"
            else -> "sunny"
        }

//        var cachedDrawableId: Int? = null
//
//        val drawableResourceId: Int = cachedDrawableId ?: run get@{
//            cachedDrawableId = binding.root.resources.getIdentifier(icon, "drawable", binding.root.context.packageName)
//            return@get cachedDrawableId ?: 0
//        }

        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            icon,
            "drawable",
            binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.pic)
    }

}