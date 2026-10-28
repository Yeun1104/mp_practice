package com.example.mp0703

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var signObj: ImageView           // 왼쪽 화살표 그림
    lateinit var rightLayoutObj: LinearLayout // 오른쪽 빈 칸

    // 동물 이미지 (image1 = 가장 작음 ~ image6 = 가장 큼)
    var ivDrawableId = intArrayOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6
    )
    var imageviewId = intArrayOf(
        R.id.imageView1, R.id.imageView2, R.id.imageView3,
        R.id.imageView4, R.id.imageView5, R.id.imageView6
    )
    var ivObj = arrayOfNulls<ImageView>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 처음엔 동물 순서를 무작위로 섞어서 표시
        val sortImage = (0..5).shuffled()
        for (i in 0..5) {
            ivObj[i] = findViewById(imageviewId[i])
            ivObj[i]?.setImageResource(ivDrawableId[sortImage[i]])
        }

        signObj = findViewById(R.id.imageView)
        rightLayoutObj = findViewById(R.id.rightLayout)
        signObj.visibility = View.INVISIBLE   // 정렬 전엔 화살표 숨김

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // 팝업 메뉴: 버튼(button) 바로 아래에 뜸
            val popupMenu = PopupMenu(this, button)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            // 메뉴 항목 클릭 처리
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu01 -> {   // 오름차순: 작은 것 → 큰 것
                        for (i in 0..5) ivObj[i]?.setImageResource(ivDrawableId[i])
                        signObj.setImageResource(R.drawable.bg_small2big)
                        Toast.makeText(this, "오름차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                    R.id.menu02 -> {   // 내림차순: 큰 것 → 작은 것
                        for (i in 0..5) ivObj[i]?.setImageResource(ivDrawableId[5 - i])
                        signObj.setImageResource(R.drawable.bg_big2small)
                        Toast.makeText(this, "내림차순 정렬입니다", Toast.LENGTH_SHORT).show()
                    }
                }
                signObj.visibility = View.VISIBLE      // 화살표 보이기
                rightLayoutObj.visibility = View.GONE  // 오른쪽 빈 칸 없애기 → 그림이 넓어짐
                true
            }

            popupMenu.show()
        }
    }
}