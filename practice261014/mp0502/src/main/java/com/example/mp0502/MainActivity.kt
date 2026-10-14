package com.example.mp0502

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var cb1: CheckBox   // 소방차
    lateinit var cb2: CheckBox   // 핸드믹서
    lateinit var cb3: CheckBox   // 청진기
    lateinit var cb4: CheckBox   // 장갑
    lateinit var btnResult: Button
    lateinit var tvResult: TextView

    //결과를 이미 봤는지 (버튼이 '재시작하기' 상태인지)
    var isFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cb1 = findViewById(R.id.checkBox1)
        cb2 = findViewById(R.id.checkBox2)
        cb3 = findViewById(R.id.checkBox3)
        cb4 = findViewById(R.id.checkBox4)
        btnResult = findViewById(R.id.buttonResult)
        tvResult = findViewById(R.id.textViewResult)

        //체크 상태가 바뀔 때 호출되는 리스너 (4개가 같이 사용)
        val checkListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val item = when (buttonView.id) {
                    R.id.checkBox1 -> "소방차를"
                    R.id.checkBox2 -> "핸드믹서를"
                    R.id.checkBox3 -> "청진기를"
                    else -> "장갑을"
                }
                Toast.makeText(this, "$item 선택했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        cb1.setOnCheckedChangeListener(checkListener)
        cb2.setOnCheckedChangeListener(checkListener)
        cb3.setOnCheckedChangeListener(checkListener)
        cb4.setOnCheckedChangeListener(checkListener)

        //결과보기 / 재시작하기 버튼
        btnResult.setOnClickListener {
            if (isFinished) restart() else showResult()
        }
    }

    //결과보기
    fun showResult() {
        if (!cb1.isChecked && !cb2.isChecked && !cb3.isChecked && !cb4.isChecked) {
            Toast.makeText(this, "용품을 하나 이상 선택하세요", Toast.LENGTH_SHORT).show()
            return
        }

        //정답: 소방차 + 장갑만 선택
        val correct = cb1.isChecked && !cb2.isChecked && !cb3.isChecked && cb4.isChecked

        tvResult.text = if (correct) "맞았습니다" else "틀렸습니다"
        btnResult.text = "재시작하기"
        isFinished = true
    }

    //재시작하기: 처음 상태로
    fun restart() {
        cb1.isChecked = false
        cb2.isChecked = false
        cb3.isChecked = false
        cb4.isChecked = false
        tvResult.text = "소방관의 용품을 선택해주세요"
        btnResult.text = "결과보기"
        isFinished = false
    }
}