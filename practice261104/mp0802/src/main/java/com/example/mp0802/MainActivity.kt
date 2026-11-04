package com.example.mp0802

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var ivQuestion: ImageView
    lateinit var ivAnswer: ImageView
    lateinit var ivAmPm: ImageView

    var question = 0   // 문제 번호 (0~4)
    var ampm = 0       // 0 = 오전, 1 = 오후

    // 시계 문제 이미지
    val drawableQuestion = intArrayOf(
        R.drawable.question1, R.drawable.question2, R.drawable.question3,
        R.drawable.question4, R.drawable.question5
    )
    // 정답 숫자 이미지 (오전용 / 오후용)
    val drawableAnswerAM = intArrayOf(
        R.drawable.answer11, R.drawable.answer12, R.drawable.answer13,
        R.drawable.answer14, R.drawable.answer15
    )
    val drawableAnswerPM = intArrayOf(
        R.drawable.answer21, R.drawable.answer22, R.drawable.answer23,
        R.drawable.answer24, R.drawable.answer25
    )
    // 정답 시각 (24시간 기준 "시:분")
    val timeAnswerAM = arrayOf("9:15", "2:25", "10:35", "11:40", "3:10")
    val timeAnswerPM = arrayOf("21:15", "14:25", "22:35", "23:40", "15:10")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivQuestion = findViewById(R.id.question)
        ivAnswer = findViewById(R.id.answer)
        ivAmPm = findViewById(R.id.ampm)

        newQuestion()   // 첫 문제

        // 다시 시작하기
        val buttonStart: Button = findViewById(R.id.buttonStart)
        buttonStart.setOnClickListener { newQuestion() }

        // 시계 불러오기 → 시간 선택 대화상자
        val buttonTime: Button = findViewById(R.id.buttonTime)
        buttonTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)   // 처음 보여줄 시각 = 현재 시각
            val minute = calendar.get(Calendar.MINUTE)

            // 사용자가 시간을 고르고 OK를 눌렀을 때
            val listener = TimePickerDialog.OnTimeSetListener { timePicker, selHour, selMinute ->
                val selected = "$selHour:$selMinute"
                val isPM = selHour >= 12

                if (ampm == 0 && !isPM && selected == timeAnswerAM[question]) {
                    showCorrect(drawableAnswerAM[question])
                } else if (ampm == 1 && isPM && selected == timeAnswerPM[question]) {
                    showCorrect(drawableAnswerPM[question])
                } else {
                    Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
                }
            }

            // TimePickerDialog(컨텍스트, 테마, 리스너, 시, 분, 24시간제 여부)
            val timePickerDialog = TimePickerDialog(this, R.style.TimePickerStyle, listener, hour, minute, true)
            timePickerDialog.show()
        }
    }

    // 새 문제: 시계 5개 중 하나 + 오전/오후 랜덤
    fun newQuestion() {
        question = (0..4).random()
        ampm = (0..1).random()
        ivQuestion.setImageResource(drawableQuestion[question])
        ivAmPm.setImageResource(if (ampm == 0) R.drawable.time_am else R.drawable.time_pm)
        ivAnswer.isVisible = false
    }

    // 정답 처리: 말풍선 안에 정답 시각 그림 표시
    fun showCorrect(answerImage: Int) {
        ivAnswer.setImageResource(answerImage)
        ivAnswer.isVisible = true
        Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
    }
}