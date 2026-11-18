package com.example.mp1004

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// 첫 화면: 추가(작성 화면으로) / 검색(기간 선택 후 목록 표시)
class MainActivity : AppCompatActivity() {
    lateinit var findFromTxt: TextView
    lateinit var findToTxt: TextView
    lateinit var recyclerView: RecyclerView
    var fromDate = ""
    var toDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findFromTxt = findViewById(R.id.findFromTxt)
        findToTxt = findViewById(R.id.findToTxt)
        recyclerView = findViewById(R.id.FindRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 추가 → 작성 화면
        findViewById<Button>(R.id.button_insert).setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

        // 검색 → 기간 선택 대화상자
        findViewById<Button>(R.id.button_search).setOnClickListener {
            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("검색 기간을 선택하세요")
                .build()
            picker.addOnPositiveButtonClickListener { range ->
                // 달력이 주는 값은 UTC 기준 시간(ms) → UTC로 날짜 문자열 변환
                val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                fmt.timeZone = TimeZone.getTimeZone("UTC")
                fromDate = fmt.format(range.first)
                toDate = fmt.format(range.second)
                findFromTxt.text = fromDate
                findToTxt.text = toDate
                showList()
            }
            picker.show(supportFragmentManager, "dateRange")
        }
    }

    // 작성 화면에서 돌아오면 같은 기간으로 목록 새로고침
    override fun onResume() {
        super.onResume()
        if (fromDate.isNotEmpty()) showList()
    }

    // DB에서 기간 검색 → 리사이클러 뷰에 표시
    fun showList() {
        val list = DBHelper(this).findTime(fromDate, toDate)
        recyclerView.adapter = RecyclerViewAdapter(this, list)
    }
}
