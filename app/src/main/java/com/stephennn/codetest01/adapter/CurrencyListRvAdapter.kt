package com.stephennn.codetest01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stephennn.codetest01.R
import com.stephennn.codetest01.model.CurrencyInfo

class CurrencyListRvAdapter : RecyclerView.Adapter<CurrencyListRvAdapter.MyViewHolder>() {
    private val dataList = mutableListOf<CurrencyInfo>()
    private var rvOnItemClickListener: RvOnItemClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list: List<CurrencyInfo>) {
        /** update recyclerView ui by passing a new data list **/
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClick(listener: RvOnItemClickListener) {
        rvOnItemClickListener = listener
    }

    interface RvOnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_currency_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int = dataList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvLogo: TextView = itemView.findViewById(R.id.tv_logo)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvSymbol: TextView = itemView.findViewById(R.id.tv_symbol)

        @SuppressLint("SetTextI18n")
        fun bind(s: CurrencyInfo, position: Int) {
            /** setup ui and onClickListener **/
            tvLogo.text = s.name[0].toString()
            tvName.text = s.name
            tvSymbol.text = "${s.symbol} >"
            itemView.setOnClickListener {
                rvOnItemClickListener?.onItemClick(position)
            }
        }
    }
}