package com.example.mp0601

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        // Handler 객체 생성: UI 스레드에서 실행될 작업을 처리하기 위한 핸들러 생성
        val handler = Handler(Looper.getMainLooper())
        // postDelayed를 사용하여 2초 후 작업 실행
        handler.postDelayed({
            // TODO 1: LoginActivity로 이동하는 인텐트 생성 (명시적 인텐트: 이동할 클래스를 직접 지정)
            val intent = Intent(this, LoginActivity::class.java)
            // TODO 2: 생성한 인텐트를 사용하여 LoginActivity로 이동
            startActivity(intent)
            // (추가) 스플래시 화면 종료 → 로그인 화면에서 뒤로가기 눌러도 스플래시로 안 돌아옴
            finish()
        }, 2000)
    }
}