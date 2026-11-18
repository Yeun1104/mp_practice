package com.example.mp1001

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ivNumA: ImageView
    lateinit var ivNumB: ImageView
    lateinit var ivBackground: ImageView
    lateinit var etAnswer: EditText
    lateinit var tvMessage: TextView

    // 숫자 이미지 (0~9)
    val numberImages = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
        R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9
    )

    var correctCount = 0   // 정답 개수
    var totalCount = 0     // 푼 문제 개수
    var answerValue = 0    // 현재 문제의 정답 (result라는 이름은 Activity.setResult와 겹쳐서 사용 불가)

    // 공유 환경 설정 파일 이름 / 저장 키
    val PREF_NAME = "gamefile"
    val KEY_TOTAL = "total"
    val KEY_GRADE = "grade"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivNumA = findViewById(R.id.imageView_num1)
        ivNumB = findViewById(R.id.imageView_num2)
        ivBackground = findViewById(R.id.imageView_bg)
        etAnswer = findViewById(R.id.editText_answer)
        tvMessage = findViewById(R.id.textView_message)

        loadGameStats()       // 앱을 다시 켜도 이전 기록을 불러옴
        generateQuestion()    // 첫 문제

        // 다시시작하기: 저장된 기록 전부 삭제
        findViewById<Button>(R.id.button_reset).setOnClickListener {
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
                clear()
            }
            loadGameStats()
            ivBackground.setImageResource(R.drawable.bg_quiz)
            etAnswer.text.clear()
            generateQuestion()
        }

        // 정답확인하기
        findViewById<Button>(R.id.button_result).setOnClickListener {
            val answer = etAnswer.text.toString().toIntOrNull()
            if (answer == null) {
                Toast.makeText(this, "정답을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            totalCount += 1
            if (answer == answerValue) {
                correctCount += 1
                ivBackground.setImageResource(R.drawable.bg_ok)
                Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
            } else {
                ivBackground.setImageResource(R.drawable.bg_no)
                Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
            }

            // 공유 환경 설정 파일에 저장
            getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit {
                putInt(KEY_TOTAL, totalCount)
                putInt(KEY_GRADE, correctCount)
            }

            loadGameStats()
            generateQuestion()
            etAnswer.text.clear()
        }
    }

    // 구구단 문제 만들기 (1~9단)
    fun generateQuestion() {
        val partA = (1..9).random()
        val partB = (1..9).random()
        answerValue = partA * partB
        ivNumA.setImageResource(numberImages[partA])
        ivNumB.setImageResource(numberImages[partB])
    }

    // 공유 환경 설정 파일에서 기록을 읽어 정답률 표시
    fun loadGameStats() {
        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        totalCount = pref.getInt(KEY_TOTAL, 0)
        correctCount = pref.getInt(KEY_GRADE, 0)
        if (totalCount > 0) {
            val percent = correctCount * 100.0 / totalCount
            tvMessage.text = "당신의 정답률은 %.2f%% (%d/%d) 입니다".format(percent, correctCount, totalCount)
        } else {
            tvMessage.text = "당신의 정답률은 0% 입니다"
        }
    }
}
