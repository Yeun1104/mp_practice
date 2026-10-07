package com.example.mp0404

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var etObjA: EditText
    lateinit var etObjB: EditText

    val answerA = 2 // 거북이
    val answerB = 5 // 물고기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etObjA = findViewById(R.id.editTextA)
        etObjB = findViewById(R.id.editTextB)
    }

    //버튼 클릭 시 호출되는 메서드
    fun onClickChoice(view: View?) {
        //빈 칸이면 toInt()에서 앱이 죽으므로 먼저 확인
        if (etObjA.text.isEmpty() || etObjB.text.isEmpty()) {
            Toast.makeText(this, "개수를 모두 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        //입력된 값을 정수로 변환하여 변수에 저장
        val partA = etObjA.text.toString().toInt()
        val partB = etObjB.text.toString().toInt()

        if (partA == answerA && partB == answerB) {
            Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }
}