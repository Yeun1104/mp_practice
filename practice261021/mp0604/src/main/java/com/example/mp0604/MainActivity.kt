package com.example.mp0604

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        val editText: EditText = findViewById(R.id.editText)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            var url = editText.text.toString().trim()   // 입력한 주소 (앞뒤 공백 제거)

            if (url.isEmpty()) {
                Toast.makeText(this, "URL을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // www.hanbit.co.kr 처럼 http(s):// 없이 입력하면 브라우저가 못 열어서 붙여줌
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }

            // 암시적 인텐트: "이 주소를 보여줘" → 브라우저 앱이 실행됨
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // 이 동작을 처리할 앱(브라우저)이 없을 때
                Toast.makeText(this, "웹 페이지를 열 수 있는 앱이 없습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}