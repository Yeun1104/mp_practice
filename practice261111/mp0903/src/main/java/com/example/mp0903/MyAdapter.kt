package com.example.mp0903

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(val catsList: ArrayList<MyItem>) : BaseAdapter() {

    override fun getCount(): Int = catsList.size

    override fun getItem(position: Int): Any = catsList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    // 스피너의 한 칸 (닫혀 있을 때 보이는 칸 + 펼쳤을 때 목록 칸 모두 이걸로 그림)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
            ?: LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item, parent, false)

        val cat = catsList[position]
        view.findViewById<TextView>(R.id.tvName).text = cat.name
        view.findViewById<ImageView>(R.id.imgCat).setImageResource(cat.image)
        return view
    }
}