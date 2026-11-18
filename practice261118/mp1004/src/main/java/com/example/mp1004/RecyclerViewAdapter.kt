package com.example.mp1004

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val context: Context, private val list: ArrayList<HouseKeeping>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rcSeq: TextView = itemView.findViewById(R.id.RcSeq)
        val rcCategory: TextView = itemView.findViewById(R.id.RcCategory)
        val rcPurpose: TextView = itemView.findViewById(R.id.Rcpurpose)
        val rcDate: TextView = itemView.findViewById(R.id.RCdate)
        val rcMoney: TextView = itemView.findViewById(R.id.RcMoney)
        val rcMemo: TextView = itemView.findViewById(R.id.RcMemo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.rcSeq.text = item.seq.toString()
        holder.rcCategory.text = item.category
        holder.rcPurpose.text = item.purpose
        holder.rcDate.text = item.date
        holder.rcMoney.text = item.money.toString()
        holder.rcMemo.text = item.memo
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
