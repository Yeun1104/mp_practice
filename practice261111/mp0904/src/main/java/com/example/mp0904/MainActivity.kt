package com.example.mp0904

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var fruitTotalText: TextView
    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ① 데이터: 바나나 30, 키위 40, 딸기 60, 포도 80, 사과 100
        fruitList.add(Fruit(R.drawable.fruit1, 30))
        fruitList.add(Fruit(R.drawable.fruit2, 40))
        fruitList.add(Fruit(R.drawable.fruit3, 60))
        fruitList.add(Fruit(R.drawable.fruit4, 80))
        fruitList.add(Fruit(R.drawable.fruit5, 100))

        fruitTotalText = findViewById(R.id.total)
        fruitTotalText.text = "0"

        // ② 어댑터 + 레이아웃 매니저 연결
        val adapter = OrderAdapter(fruitList)
        val recyclerView: RecyclerView = findViewById(R.id.fruit_list)
        recyclerView.layoutManager = LinearLayoutManager(this)   // 세로 목록으로 배치
        recyclerView.adapter = adapter

        // ③ 수량이 바뀌거나 삭제되면 총계 다시 계산
        adapter.setOnSelectChangedListener(object : OrderAdapter.OnSelectChangedListener {
            override fun onSelectChanged(items: ArrayList<Fruit>) {
                fruitTotalText.text = calculateFruitTotal().toString()
            }
        })
    }

    // 총계 = 각 과일의 (가격 × 수량) 합
    fun calculateFruitTotal(): Int {
        var total = 0
        for (fruit in fruitList) total += fruit.mPrice * fruit.mQuantity
        return total
    }
}