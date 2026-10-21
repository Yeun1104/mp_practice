package com.example.mp0603

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    // 그림 이미지뷰 / 감싸는 프레임 레이아웃 ID (인덱스 0~8 = 01~09)
    var imageviewId = intArrayOf(
        R.id.image01, R.id.image02, R.id.image03,
        R.id.image04, R.id.image05, R.id.image06,
        R.id.image07, R.id.image08, R.id.image09
    )
    var framelayoutId = intArrayOf(
        R.id.frame_layout01, R.id.frame_layout02, R.id.frame_layout03,
        R.id.frame_layout04, R.id.frame_layout05, R.id.frame_layout06,
        R.id.frame_layout07, R.id.frame_layout08, R.id.frame_layout09
    )
    var ivObj: Array<ImageView?> = arrayOfNulls<ImageView>(9)
    var flayoutObj: Array<FrameLayout?> = arrayOfNulls<FrameLayout>(9)

    var imgArray: Array<ImageView?> = arrayOfNulls<ImageView>(9)  // 체크 표시 이미지
    var selectNum = IntArray(9)   // 선택 여부 (1 = 선택)
    var mShape = 0                // 첫 화면에서 받은 모양 (1 = 원, 2 = 사각형)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 인텐트로 전달된 모양 받기
        mShape = intent.getIntExtra("shape", 0)

        val shapeObj: ImageView = findViewById(R.id.imageView)
        if (mShape == 1) shapeObj.setImageResource(R.drawable.circle)
        else if (mShape == 2) shapeObj.setImageResource(R.drawable.square)

        for (i in 0..8) {
            selectNum[i] = 0
            ivObj[i] = findViewById(imageviewId[i])
            flayoutObj[i] = findViewById(framelayoutId[i])
        }

        var num = 0
        // 그림 클릭: 선택 / 선택 취소 (최대 3개)
        for (i in 0..8) {
            ivObj[i]?.setOnClickListener {
                if (selectNum[i] == 1) {
                    // 이미 선택된 그림 → 선택 취소 (체크 그림 제거)
                    selectNum[i] = 0
                    num--
                    flayoutObj[i]?.removeView(imgArray[i])
                } else {
                    if (num < 3) {
                        // 새로 선택 → 체크 그림을 만들어 프레임 위에 겹침
                        selectNum[i] = 1
                        num++
                        imgArray[i] = ImageView(this)
                        imgArray[i]?.setImageResource(R.drawable.ok_check)
                        flayoutObj[i]?.addView(imgArray[i])
                    } else {
                        Toast.makeText(this, "3개 까지만 선택 가능합니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 결과보기: 선택 상태 배열을 담아 돌려주고 종료
        val btnObj: Button = findViewById(R.id.button)
        btnObj.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("resId", selectNum)
            setResult(RESULT_OK, resultIntent)   // 결과 전달
            finish()                             // 현재 액티비티 종료 → 첫 화면으로
        }
    }
}