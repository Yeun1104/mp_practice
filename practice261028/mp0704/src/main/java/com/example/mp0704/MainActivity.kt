package com.example.mp0704

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // 앱 바에 표시되는 찬성/반대 숫자
    var okCount: TextView? = null
    var noCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 앱 바를 쓰는 예제라 enableEdgeToEdge()는 쓰지 않음 (교수님 코드와 동일)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonOk: Button = findViewById(R.id.button_ok)
        val buttonNo: Button = findViewById(R.id.button_no)

        // 찬성 버튼 → 앱 바의 찬성 숫자 +1
        buttonOk.setOnClickListener { increase(okCount) }
        // 반대 버튼 → 앱 바의 반대 숫자 +1
        buttonNo.setOnClickListener { increase(noCount) }
    }

    // 텍스트뷰의 숫자를 1 올림
    fun increase(tv: TextView?) {
        if (tv == null) return
        val num = tv.text.toString().toInt() + 1
        tv.text = num.toString()
    }

    // 앱 바 메뉴 만들기 + 액션 레이아웃 안의 숫자 TextView 찾아두기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val okLayout = menu?.findItem(R.id.menu_up)?.actionView
        okCount = okLayout?.findViewById(R.id.thumbs_up_num)

        val noLayout = menu?.findItem(R.id.menu_down)?.actionView
        noCount = noLayout?.findViewById(R.id.thumbs_down_num)

        return super.onCreateOptionsMenu(menu)
    }

    // ⋮ 메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_init -> {          // 초기화
                okCount?.text = "0"
                noCount?.text = "0"
                return true
            }
            R.id.menu_close -> {         // 종료
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}