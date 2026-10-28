package com.example.mp0705

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 툴 바에 표시되는 정답/오답 횟수
    var okCount: TextView? = null
    var noCount: TextView? = null

    // 과일 이미지와 개수 (체리=3, 망고=6, 사과=8, 바나나=1)
    val fruit = intArrayOf(R.drawable.fruit1, R.drawable.fruit2, R.drawable.fruit3, R.drawable.fruit4)
    val fruitCount = intArrayOf(3, 6, 8, 1)

    val imageviewId = intArrayOf(R.id.fruit1, R.id.fruit2, R.id.fruit3)
    val operatorId = intArrayOf(R.id.op1, R.id.op2)
    val operatorSign = arrayOf("+", "-")

    // 현재 문제: 어떤 과일(인덱스) 3개, 어떤 연산자(0='+', 1='-') 2개
    var fruitImage = listOf(0, 1, 2)
    var operator = IntArray(2)

    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 툴 바를 쓰는 예제라 enableEdgeToEdge()는 쓰지 않음 (교수님 코드와 동일)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ★ 레이아웃의 Toolbar를 이 화면의 앱 바로 지정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        editText = findViewById(R.id.answer)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)

        makeQuestion()   // 첫 문제

        // 결과보기
        button2.setOnClickListener {
            // 정답 계산: 첫 과일 개수에서 시작해 연산자대로 더하기/빼기
            var result = fruitCount[fruitImage[0]]
            for (i in 0..1) {
                if (operator[i] == 0) result += fruitCount[fruitImage[i + 1]]
                else result -= fruitCount[fruitImage[i + 1]]
            }

            val answer = editText.text.toString().toIntOrNull()
            if (answer == null) {
                Toast.makeText(this, "입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (answer == result) {
                Toast.makeText(this, "맞았습니다", Toast.LENGTH_SHORT).show()
                increase(okCount)
            } else {
                Toast.makeText(this, "틀렸습니다", Toast.LENGTH_SHORT).show()
                increase(noCount)
            }
        }

        // 다시 시작하기: 입력 지우고 새 문제
        button1.setOnClickListener {
            editText.text.clear()
            makeQuestion()
        }
    }

    // 무작위 문제 만들기 (과일은 체리/망고/사과 3개를 섞어서 사용)
    fun makeQuestion() {
        fruitImage = listOf(0, 1, 2).shuffled()
        for (i in 0..2) {
            findViewById<ImageView>(imageviewId[i]).setImageResource(fruit[fruitImage[i]])
        }
        for (i in 0..1) {
            operator[i] = (0..1).random()
            findViewById<TextView>(operatorId[i]).text = operatorSign[operator[i]]
        }
    }

    // 텍스트뷰 숫자 +1
    fun increase(tv: TextView?) {
        if (tv == null) return
        tv.text = (tv.text.toString().toInt() + 1).toString()
    }

    // 툴 바 메뉴 만들기 + 액션 레이아웃 안의 숫자 TextView 찾아두기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        okCount = menu?.findItem(R.id.menu_up)?.actionView?.findViewById(R.id.yes_num)
        noCount = menu?.findItem(R.id.menu_down)?.actionView?.findViewById(R.id.no_num)
        return super.onCreateOptionsMenu(menu)
    }

    // ⋮ 메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_init -> {
                okCount?.text = "0"
                noCount?.text = "0"
                return true
            }
            R.id.menu_close -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}