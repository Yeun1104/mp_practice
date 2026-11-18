package com.example.mp1004

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

// 가계부 작성 화면
class InsertActivity : AppCompatActivity() {
    lateinit var dateTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.insert)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dateTxt = findViewById(R.id.textview_date)
        val radioGroup: RadioGroup = findViewById(R.id.radio_group)
        val purposeEdit: EditText = findViewById(R.id.edit_purpose)
        val moneyEdit: EditText = findViewById(R.id.edit_money)
        val memoEdit: EditText = findViewById(R.id.edit_content)

        var category = "지출"            // 기본: 지출
        setDate(Calendar.getInstance())  // 기본: 오늘

        // 오늘 버튼
        findViewById<Button>(R.id.button_today).setOnClickListener {
            setDate(Calendar.getInstance())
        }

        // 달력 버튼 → 날짜 선택 대화상자 (8장)
        findViewById<Button>(R.id.button_calendar).setOnClickListener {
            val now = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                val picked = Calendar.getInstance()
                picked.set(y, m, d)
                setDate(picked)
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 수입 / 지출
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            category = if (checkedId == R.id.radio_income) "수입" else "지출"
        }

        // 저장 → DB에 INSERT
        findViewById<Button>(R.id.button_insert).setOnClickListener {
            val purpose = purposeEdit.text.toString().trim()
            val money = moneyEdit.text.toString().toIntOrNull()
            if (purpose.isEmpty() || money == null) {
                Toast.makeText(this, "용도와 금액을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = HouseKeeping(0, category, purpose, dateTxt.text.toString(), money,
                memoEdit.text.toString().trim())
            DBHelper(this).insert(item)
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()

            purposeEdit.text.clear()
            moneyEdit.text.clear()
            memoEdit.text.clear()
        }

        // 목록 → 이 화면을 닫고 첫 화면(MainActivity)으로 돌아감
        findViewById<Button>(R.id.button_list).setOnClickListener {
            finish()
        }
    }

    // 날짜를 yyyy-MM-dd 형식으로 표시 (DB 검색 때 문자열 비교가 되도록 0을 채움)
    fun setDate(cal: Calendar) {
        dateTxt.text = "%04d-%02d-%02d".format(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)
        )
    }
}
