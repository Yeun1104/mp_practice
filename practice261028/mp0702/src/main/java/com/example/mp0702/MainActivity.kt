package com.example.mp0702

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ivObj01: ImageView   // 고양이 쪽 동그라미
    lateinit var ivObj02: ImageView   // 강아지 쪽 동그라미

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivObj01 = findViewById(R.id.ivSign1)
        ivObj02 = findViewById(R.id.ivSign2)

        // ① 길게 눌렀을 때 컨텍스트 메뉴가 뜨도록 뷰를 등록
        registerForContextMenu(ivObj01)
        registerForContextMenu(ivObj02)
    }

    // ② 컨텍스트 메뉴 만들기: 어느 뷰를 눌렀는지(v.id)에 따라 다른 메뉴
    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when (v?.id) {
            R.id.ivSign1 -> {
                menu?.setHeaderTitle("고양이 비교 메뉴")
                menuInflater.inflate(R.menu.cat_menu, menu)
            }
            R.id.ivSign2 -> {
                menu?.setHeaderTitle("강아지 비교 메뉴")
                menuInflater.inflate(R.menu.dog_menu, menu)
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    // ③ 메뉴 항목을 골랐을 때
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 고양이: 정답은 '크다'
            R.id.item11 -> { ivObj01.setImageResource(R.drawable.sign_great); showToast(true);  return true }
            R.id.item12 -> { ivObj01.setImageResource(R.drawable.sign_small); showToast(false); return true }
            R.id.item13 -> { ivObj01.setImageResource(R.drawable.sign_equal); showToast(false); return true }
            // 강아지: 정답은 '작다'
            R.id.item21 -> { ivObj02.setImageResource(R.drawable.sign_great); showToast(false); return true }
            R.id.item22 -> { ivObj02.setImageResource(R.drawable.sign_small); showToast(true);  return true }
            R.id.item23 -> { ivObj02.setImageResource(R.drawable.sign_equal); showToast(false); return true }
        }
        return super.onContextItemSelected(item)
    }

    // 정답/오답 토스트
    fun showToast(correct: Boolean) {
        val msg = if (correct) "맞았습니다" else "틀렸습니다"
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}