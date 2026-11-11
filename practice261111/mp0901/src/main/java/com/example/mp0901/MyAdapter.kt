package com.example.mp0901

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

// 데이터 목록(itemList)을 리스트 뷰의 "줄"로 바꿔주는 어댑터
class MyAdapter(val itemList: ArrayList<MyItem>) : BaseAdapter() {

    // 전체 줄 수
    override fun getCount(): Int = itemList.size

    // position번째 데이터
    override fun getItem(position: Int): Any = itemList[position]

    // position번째 줄의 id (여기선 위치를 그대로 사용)
    override fun getItemId(position: Int): Long = position.toLong()

    // ★ position번째 줄의 화면을 만들어서 반환
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 스크롤로 사라진 줄(convertView)이 있으면 재사용, 없으면 새로 만듦
        val view = convertView
            ?: LayoutInflater.from(parent?.context).inflate(R.layout.listview_item, parent, false)

        val item = itemList[position]
        view.findViewById<TextView>(R.id.textView1).text = item.name
        view.findViewById<TextView>(R.id.textView2).text = item.character
        view.findViewById<TextView>(R.id.textView3).text = item.weight
        view.findViewById<ImageView>(R.id.imageView).setImageResource(item.image)
        return view
    }
}