package com.example.mp0701

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var ivObjA: ImageView
    lateinit var ivObjOp: ImageView
    lateinit var ivObjResult: ImageView
    lateinit var ivObjResult0: ImageView

    // 칠판에 쓸 숫자 이미지 (0~9)
    var number = intArrayOf(
        R.drawable.num0, R.drawable.num1, R.drawable.num2, R.drawable.num3,
        R.drawable.num4, R.drawable.num5, R.drawable.num6, R.drawable.num7,
        R.drawable.num8, R.drawable.num9
    )
    // 보기 버튼 숫자 이미지 (0~9)
    var btnNumber = intArrayOf(
        R.drawable.btn0, R.drawable.btn1, R.drawable.btn2, R.drawable.btn3,
        R.drawable.btn4, R.drawable.btn5, R.drawable.btn6, R.drawable.btn7,
        R.drawable.btn8, R.drawable.btn9
    )
    var buttonChoice = intArrayOf(R.id.buttonChoice1, R.id.buttonChoice2, R.id.buttonChoice3)
    var btnObjChoice: Array<ImageButton?> = arrayOfNulls<ImageButton>(3)
    var select = 0   // 정답이 들어간 버튼 위치 (0~2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivObjA = findViewById(R.id.imageViewA)
        ivObjOp = findViewById(R.id.imageViewOp)
        ivObjResult0 = findViewById(R.id.imageViewResult0)
        ivObjResult = findViewById(R.id.imageViewResult)
        for (i in 0..2) {
            btnObjChoice[i] = findViewById(buttonChoice[i])
            btnObjChoice[i]?.setOnClickListener(this)   // 클릭은 아래 onClick()에서 처리
        }

        calculate("+")   // 처음엔 더하기 문제
    }

    // 문제 만들기: A (연산자) B = 결과 에서 B를 맞히는 문제
    fun calculate(operator: String) {
        var partA = (Math.random() * 9).roundToInt()   // 0~9
        var partB = (Math.random() * 9).roundToInt()
        var result = 0

        when (operator) {
            "+" -> {
                result = partA + partB
                ivObjOp.setImageResource(R.drawable.op1)
            }
            "-" -> {
                result = partA - partB
                ivObjOp.setImageResource(R.drawable.op2)
            }
            "*" -> {
                result = partA * partB
                ivObjOp.setImageResource(R.drawable.op3)
            }
            "%" -> {
                if (partA == 0) partA = 1
                if (partB == 0) partB = 1
                // A가 B보다 작으면 두 수를 바꿈
                if (partA < partB) {
                    val swap = partA
                    partA = partB
                    partB = swap
                }
                result = partA % partB
                ivObjOp.setImageResource(R.drawable.op4)
            }
        }

        // 오답 보기 2개 (정답 ±1, 0~9 범위로 순환)
        var wrongAnswer1 = partB - 1
        if (wrongAnswer1 < 0) wrongAnswer1 = 9
        var wrongAnswer2 = partB + 1
        if (wrongAnswer2 > 9) wrongAnswer2 = 0

        // 칠판에 A와 결과 표시 (B 자리는 '?' 그대로)
        ivObjA.setImageResource(number[partA])
        ivObjResult0.visibility = View.VISIBLE
        if (result < 0) ivObjResult0.setImageResource(R.drawable.op2)          // 음수면 '-' 표시
        else if (result < 10) ivObjResult0.visibility = View.GONE               // 한 자리면 숨김
        else ivObjResult0.setImageResource(number[result / 10])                 // 십의 자리
        if (result < 0) ivObjResult.setImageResource(number[(result * -1) % 10])
        else ivObjResult.setImageResource(number[result % 10])                  // 일의 자리

        // 정답 버튼 위치를 랜덤으로 정하고 나머지 두 칸에 오답 배치
        val index = (Math.random() * 3).toInt()
        val index2 = (index + 1) % 3
        val index3 = (index + 2) % 3
        select = index
        btnObjChoice[index]?.setBackgroundResource(btnNumber[partB])
        btnObjChoice[index2]?.setBackgroundResource(btnNumber[wrongAnswer1])
        btnObjChoice[index3]?.setBackgroundResource(btnNumber[wrongAnswer2])
    }

    // 보기 버튼 클릭: 누른 버튼이 정답 위치인지 확인
    override fun onClick(view: View) {
        if (view.id == buttonChoice[select])
            Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
    }

    // ① 옵션 메뉴 만들기: option_menu.xml을 앱 바에 붙임
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // ② 메뉴 항목을 눌렀을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item01 -> { calculate("+"); return true }
            R.id.item02 -> { calculate("-"); return true }
            R.id.item03 -> { calculate("*"); return true }
            R.id.item04 -> { calculate("%"); return true }
        }
        return super.onOptionsItemSelected(item)
    }
}