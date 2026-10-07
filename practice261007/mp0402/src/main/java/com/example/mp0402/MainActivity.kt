package com.example.mp0402

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //정답 버튼 (왼쪽 고양이 < 오른쪽 고양이)
    val answerId = R.id.imageButton2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //비교 연산자 버튼 클릭 시 호출 (XML의 android:onClick)
    fun onClickChoice(view: View) {
        if (view.id == answerId) {
            Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }
}