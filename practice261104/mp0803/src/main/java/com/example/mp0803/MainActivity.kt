package com.example.mp0803

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView   // 아랫줄: 입력 중인 숫자
    private lateinit var tvInput: TextView    // 윗줄: 식 (예: 50 - 6 = 44)

    private var tvResultCheck = false   // 지금 숫자를 입력하는 중인지
    private var tvInputCheck = false    // 첫 번째 숫자 + 연산자가 정해졌는지
    private var firstNum = 0.0
    private var secondNum = 0.0
    private var resultNum = 0.0
    private var arithOper = ""          // "+", "-", "x", "÷"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            // ① 내가 만든 레이아웃(dialog_calculate.xml)을 뷰로 만들기
            val dialogView = layoutInflater.inflate(R.layout.dialog_calculate, null)
            // ② 대화상자에 그 뷰를 넣고 띄우기
            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            val dialog = builder.show()

            // ③ 대화상자 안의 뷰는 dialogView.findViewById로 찾아야 함
            tvResult = dialogView.findViewById(R.id.tvResult)
            tvInput = dialogView.findViewById(R.id.tvInput)
            resetAll()   // 열 때마다 새 계산

            val buttonClose: Button = dialogView.findViewById(R.id.buttonclose)
            buttonClose.setOnClickListener { dialog.dismiss() }
        }
    }

    // 숫자 표시: 정수면 소수점 없이 (6.0 → 6)
    private fun fmt(num: Double): String =
        if (num % 1.0 == 0.0) num.toLong().toString() else num.toString()

    // 계산 (소수점 9자리에서 반올림)
    private fun calculate(): Double {
        val r = when (arithOper) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "x" -> firstNum * secondNum
            else -> firstNum / secondNum
        }
        return round(r * 1000000000) / 1000000000
    }

    // 아랫줄 글자를 secondNum에 반영
    private fun updateSecondNum() {
        secondNum = tvResult.text.toString().toDoubleOrNull() ?: 0.0
    }

    private fun resetAll() {
        tvResult.text = ""
        tvInput.text = ""
        tvResultCheck = false
        tvInputCheck = false
        firstNum = 0.0
        secondNum = 0.0
        arithOper = ""
    }

    // 숫자 버튼 (android:onClick="numClick")
    fun numClick(view: View) {
        if (!tvResultCheck) tvResult.text = ""   // 새 숫자 입력 시작
        tvResult.append(view.tag.toString())
        tvResultCheck = true
        updateSecondNum()
    }

    // ← (한 글자 지우기)
    fun BackClick(view: View) {
        val str = tvResult.text.toString()
        if (str.isNotEmpty()) {
            tvResult.text = str.dropLast(1)
            updateSecondNum()
        }
    }

    // C (입력 중인 숫자만 지우기)
    fun ClearClick(view: View) {
        tvResult.text = ""
        tvResultCheck = false
        secondNum = 0.0
    }

    // AC (전부 지우기)
    fun ACClick(view: View) {
        resetAll()
    }

    // 소수점
    fun DotClick(view: View) {
        if (!tvResultCheck) {
            tvResult.text = "0."
            tvResultCheck = true
        } else if (!tvResult.text.contains(".")) {
            tvResult.append(".")
        }
    }

    // 연산자 (+ - x ÷)
    fun OperClick(view: View) {
        val op = view.tag.toString()
        if (!tvInputCheck) {
            // 첫 연산자: 지금 숫자를 첫 번째 숫자로
            firstNum = secondNum
            tvInputCheck = true
        } else if (tvResultCheck) {
            // 연속 계산 (예: 3 + 4 + → 7 +)
            firstNum = calculate()
        }
        tvInput.text = "${fmt(firstNum)} $op"
        tvResultCheck = false
        arithOper = op
    }

    // =
    fun EqualClick(view: View) {
        if (arithOper.isEmpty()) return   // 연산자 없이 = 누르면 무시
        resultNum = calculate()
        tvInput.text = "${fmt(firstNum)} $arithOper ${fmt(secondNum)} = ${fmt(resultNum)}"
        firstNum = resultNum
        tvResultCheck = false
    }
}