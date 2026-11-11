package com.example.mp0902

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(val itemList: ArrayList<MyItem>) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): Any = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
            ?: LayoutInflater.from(parent?.context).inflate(R.layout.gridview_item, parent, false)

        val item = itemList[position]
        view.findViewById<ImageView>(R.id.imgCat).setImageResource(item.image)
        view.findViewById<TextView>(R.id.tvName).text = item.name
        view.findViewById<TextView>(R.id.tvWeight).text = item.weight
        return view
    }
}