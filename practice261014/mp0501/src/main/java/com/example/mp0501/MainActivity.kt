package com.example.mp0501

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ivAnswer: ImageView

    //보기 이미지 배열
    val answerImages = intArrayOf(R.drawable.answer1, R.drawable.answer2,
        R.drawable.answer3, R.drawable.answer4)

    //정답 번호 (달팽이 껍데기)
    val correctNumber = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivAnswer = findViewById(R.id.imageViewAnswer)

        //이벤트 리스너 객체를 하나 만들어 4개 버튼에 등록
        val listener = AnswerClickListener()
        findViewById<ImageButton>(R.id.imageButton1).setOnClickListener(listener)
        findViewById<ImageButton>(R.id.imageButton2).setOnClickListener(listener)
        findViewById<ImageButton>(R.id.imageButton3).setOnClickListener(listener)
        findViewById<ImageButton>(R.id.imageButton4).setOnClickListener(listener)
    }

    //이벤트 리스너 클래스 (inner: 바깥 MainActivity의 변수에 접근 가능)
    inner class AnswerClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            //어떤 버튼을 눌렀는지 번호로 변환
            val number = when (v?.id) {
                R.id.imageButton1 -> 1
                R.id.imageButton2 -> 2
                R.id.imageButton3 -> 3
                R.id.imageButton4 -> 4
                else -> return
            }

            //선택한 껍데기를 달팽이 위에 표시
            ivAnswer.setImageResource(answerImages[number - 1])
            ivAnswer.visibility = View.VISIBLE

            if (number == correctNumber) {
                Toast.makeText(this@MainActivity, "맞았습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "틀렸습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
}