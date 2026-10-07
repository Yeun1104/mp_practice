package com.example.mp0401

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ivDiceA: ImageView
    lateinit var ivDiceB: ImageView
    lateinit var etSum: EditText

    //주사위 이미지 리소스 배열 선언
    var diceNumber = intArrayOf(R.drawable.dice1, R.drawable.dice2,
        R.drawable.dice3, R.drawable.dice4,
        R.drawable.dice5, R.drawable.dice6)

    var numA = 0
    var numB = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivDiceA = findViewById(R.id.imageViewDiceA)
        ivDiceB = findViewById(R.id.imageViewDiceB)
        etSum = findViewById(R.id.editTextSum)

        //처음 시작할 때 한 번 굴리기
        rollDice()
    }

    //주사위 굴리기: 값 랜덤 설정 + 이미지 변경
    fun rollDice() {
        numA = (Math.random() * 6).toInt() + 1
        numB = (Math.random() * 6).toInt() + 1

        ivDiceA.setImageResource(diceNumber[numA - 1])
        ivDiceB.setImageResource(diceNumber[numB - 1])
    }

    //결과보기 버튼
    fun onClickChoice(view: View) {
        val input = etSum.text.toString()
        if (input.isEmpty()) {
            Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (input.toInt() == numA + numB) {
            Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }

    //섞기 버튼: 주사위 다시 굴리고 입력칸 비우기
    fun onClickShuffle(view: View) {
        rollDice()
        etSum.setText("")
    }
}