package com.example.mp0602

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class GameActivity : AppCompatActivity() {
    // 고양이 그림의 정답 위치 이미지뷰 ID
    var imageviewCatId = intArrayOf(
        R.id.ivCheck11, R.id.ivCheck12, R.id.ivCheck13,
        R.id.ivCheck14, R.id.ivCheck15
    )
    // 강아지 그림의 정답 위치 이미지뷰 ID
    var imageviewDogId = intArrayOf(
        R.id.ivCheck21, R.id.ivCheck22, R.id.ivCheck23,
        R.id.ivCheck24, R.id.ivCheck25
    )
    // 현재 게임에서 사용할 정답 이미지뷰 5개
    var checkObj: Array<ImageView?> = arrayOfNulls<ImageView>(5)
    // 이미 찾은 위치인지 기록 (같은 곳 여러 번 눌러도 1번만 인정)
    var found = BooleanArray(5)
    var mImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 인텐트로부터 'image' 값 받기 (1 = 고양이, 2 = 강아지, 없으면 0)
        mImage = intent.getIntExtra("image", 0)

        val imageObj01: ImageView = findViewById(R.id.imageView01)
        val imageObj02: ImageView = findViewById(R.id.imageView02)
        val catLayout: ConstraintLayout = findViewById(R.id.catLayout)
        val dogLayout: ConstraintLayout = findViewById(R.id.dogLayout)

        // 고양이 선택
        if (mImage == 1) {
            imageObj01.setImageResource(R.drawable.cat01)
            imageObj02.setImageResource(R.drawable.cat02)
            for (i in 0..4) checkObj[i] = findViewById(imageviewCatId[i])
            catLayout.isVisible = true
            dogLayout.isVisible = false
        }
        // 강아지 선택
        else if (mImage == 2) {
            imageObj01.setImageResource(R.drawable.dog01)
            imageObj02.setImageResource(R.drawable.dog02)
            for (i in 0..4) checkObj[i] = findViewById(imageviewDogId[i])
            catLayout.isVisible = false
            dogLayout.isVisible = true
        }

        // 정답 위치 클릭
        var num = 0
        for (i in 0..4) {
            checkObj[i]?.setOnClickListener {
                if (!found[i]) {
                    found[i] = true
                    num++
                    checkObj[i]?.setImageResource(R.drawable.ok_select)
                    Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
                    // 5개 모두 찾은 경우
                    if (num == 5) Toast.makeText(applicationContext, "5개 모두 선택했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 정답이 아닌 곳(위쪽 그림) 클릭
        imageObj01.setOnClickListener {
            Toast.makeText(applicationContext, "틀렸습니다.", Toast.LENGTH_SHORT).show()
        }

        // 재시작하기: 현재 화면 종료 → 첫 화면으로 돌아감
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            finish()
        }
    }
}