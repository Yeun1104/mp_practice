package com.example.mp0903

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val catsList = ArrayList<MyItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ① 데이터
        catsList.add(MyItem("아비시니안", R.drawable.abyssinian))
        catsList.add(MyItem("샴", R.drawable.siamese))
        catsList.add(MyItem("앙고라", R.drawable.angora))
        catsList.add(MyItem("랙돌", R.drawable.regdol))
        catsList.add(MyItem("뱅갈", R.drawable.bengal))
        catsList.add(MyItem("스핑크스", R.drawable.sphinx))
        catsList.add(MyItem("페르시안", R.drawable.persian))

        // ② 어댑터 연결
        val spin: Spinner = findViewById(R.id.spinner)
        val image: ImageView = findViewById(R.id.imageView)
        spin.adapter = MyAdapter(catsList)

        // ③ 항목을 골랐을 때
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = catsList[position]
                image.setImageResource(item.image)
                Toast.makeText(this@MainActivity, item.name, Toast.LENGTH_SHORT).show()
            }

            // 선택이 없어졌을 때 (할 일 없음)
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}