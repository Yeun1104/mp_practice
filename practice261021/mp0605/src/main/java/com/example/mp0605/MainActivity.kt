package com.example.mp0605

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etObj01: EditText = findViewById(R.id.editText1)   // 받는 사람
        val etObj02: EditText = findViewById(R.id.editText2)   // 제목
        val etObj03: EditText = findViewById(R.id.editText3)   // 내용
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            // 이메일 보내기 암시적 인텐트
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"   // 이메일 형식 → 메일 앱들이 후보로 나옴

            // 받는 사람은 '배열'로 넣어야 함 (여러 명에게 보낼 수 있어서)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(etObj01.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, etObj02.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, etObj03.text.toString())

            // 앱 선택 창 띄우기
            val intent2 = Intent.createChooser(intent, "앱을 선택하세요")
            startActivity(intent2)
        }
    }
}