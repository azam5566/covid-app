package com.carwale.covidapp.views.dashboard.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carwale.covidapp.databinding.ListItemBinding
import com.carwale.covidapp.models.local.CountriesData
import com.carwale.covidapp.utils.Constants
import com.carwale.covidapp.utils.shared_prefrence.SharedPref


class CountryListAdapter(val context: Context?) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>(){
    private var list = mutableListOf<CountriesData>()
    var mIsSorted = false

    fun populate(l: MutableList<CountriesData>, isSorted : Boolean) {
        list = l
        mIsSorted = isSorted
        notifyDataSetChanged()
    }

    fun clear() {
        val size: Int = list.size
        list.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class MyViewHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val countryData = list[position]
        holder.itemBinding.figure1.text = countryData.Country
        holder.itemBinding.figure2.text = countryData.totalConfirmed.toString()
        holder.itemBinding.figure3.text = countryData.totalDeaths.toString()
        holder.itemBinding.figure4.text = countryData.totalRecovered.toString()

        if (!mIsSorted && countryData.Country == SharedPref.getInstance().getStringPreference(Constants.Location.COUNTRY_NAME)){
            holder.itemBinding.figure1.typeface = Typeface.DEFAULT_BOLD
            holder.itemBinding.figure2.typeface = Typeface.DEFAULT_BOLD
            holder.itemBinding.figure3.typeface = Typeface.DEFAULT_BOLD
            holder.itemBinding.figure4.typeface = Typeface.DEFAULT_BOLD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    override fun getItemCount() = list.size
    override fun getItemViewType(position: Int): Int = position

}