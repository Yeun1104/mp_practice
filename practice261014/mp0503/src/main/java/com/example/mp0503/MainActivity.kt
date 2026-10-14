package com.example.mp0503

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup

    //정답 라디오 버튼
    val answerId = R.id.radioButton2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup = findViewById(R.id.radioGroup)

        //그룹 안에서 선택된 버튼이 바뀔 때 호출
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == answerId) {
                Toast.makeText(this, "맞았습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}