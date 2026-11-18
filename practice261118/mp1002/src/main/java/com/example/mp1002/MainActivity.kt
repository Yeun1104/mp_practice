package com.example.mp1002

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.IOException
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var calendarView: CalendarView
    lateinit var editText: EditText
    lateinit var btnSave: Button
    lateinit var btnDelete: Button

    // 현재 선택한 날짜
    var year = 0
    var month = 0
    var day = 0

    // 날짜별 파일 이름 (예: 2026_11_18.txt)
    val fileName: String
        get() = "${year}_${month}_${day}.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calendarView = findViewById(R.id.calendarView)
        editText = findViewById(R.id.edtContent)
        btnSave = findViewById(R.id.save)
        btnDelete = findViewById(R.id.delete)

        // 처음엔 오늘 날짜
        val today = Calendar.getInstance()
        year = today.get(Calendar.YEAR)
        month = today.get(Calendar.MONTH) + 1   // 월은 0부터 시작하므로 +1
        day = today.get(Calendar.DAY_OF_MONTH)
        showDiary()

        // 달력에서 날짜를 바꾸면 그 날짜의 파일을 읽어옴
        calendarView.setOnDateChangeListener { _, newYear, newMonth, newDay ->
            year = newYear
            month = newMonth + 1
            day = newDay
            showDiary()
        }

        // 저장하기 / 수정하기: 내부 저장소에 파일로 쓰기
        btnSave.setOnClickListener {
            val content = editText.text.toString()
            openFileOutput(fileName, Context.MODE_PRIVATE).use { out ->
                out.write(content.toByteArray())
            }
            Toast.makeText(this, "$year/$month/$day 일정을 저장했습니다", Toast.LENGTH_SHORT).show()
            btnSave.text = "수정하기"
        }

        // 삭제하기: 선택한 날짜의 파일 삭제
        btnDelete.setOnClickListener {
            if (deleteFile(fileName)) {
                Toast.makeText(this, "$year/$month/$day 일정을 삭제했습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "삭제할 일정이 없습니다", Toast.LENGTH_SHORT).show()
            }
            showDiary()
        }
    }

    // 선택한 날짜의 일정을 화면에 표시
    fun showDiary() {
        val content = readDiary(fileName)
        editText.setText(content ?: "")
        if (content == null) {
            editText.hint = "일정 없음"
            btnSave.text = "저장하기"
        } else {
            btnSave.text = "수정하기"
        }
    }

    // 내부 저장소에서 파일 읽기 (파일이 없으면 null)
    fun readDiary(name: String): String? {
        return try {
            openFileInput(name).use { input -> input.reader().readText().trim() }
        } catch (e: IOException) {
            null
        }
    }
}
