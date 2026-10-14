package com.example.mp0505

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ivWindow: ImageView
    lateinit var ivLight: ImageView
    lateinit var ivCat: ImageView
    lateinit var swCat: Switch
    lateinit var swLight: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivWindow = findViewById(R.id.imageViewWindow)
        ivLight = findViewById(R.id.imageViewLight)
        ivCat = findViewById(R.id.imageViewCat)
        swCat = findViewById(R.id.switchCat)
        swLight = findViewById(R.id.switchLight)

        //처음 상태: 조명 꺼짐
        setLight(false)

        //고양이 스위치: ON이면 보이고, OFF면 숨김
        swCat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                ivCat.visibility = View.VISIBLE
            } else {
                ivCat.visibility = View.INVISIBLE
            }
        }

        //라이트 스위치
        swLight.setOnCheckedChangeListener { buttonView, isChecked ->
            setLight(isChecked)
        }
    }

    //조명 켜기/끄기
    fun setLight(on: Boolean) {
        if (on) {
            ivLight.setImageResource(R.drawable.light32)   //불 켜진 전구
            ivWindow.clearColorFilter()                     //창밖 풍경 원래 색
            swLight.setThumbResource(R.drawable.on3)        //손잡이: 초록 ON
        } else {
            ivLight.setImageResource(R.drawable.light31)   //불 꺼진 전구
            val gray = ColorMatrix()
            gray.setSaturation(0f)                          //채도 0 = 흑백
            ivWindow.colorFilter = ColorMatrixColorFilter(gray)
            swLight.setThumbResource(R.drawable.off3)       //손잡이: 빨강 OFF
        }
    }
}