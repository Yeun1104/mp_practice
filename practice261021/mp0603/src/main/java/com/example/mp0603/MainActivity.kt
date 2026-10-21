package com.example.mp0603

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // GameActivity를 실행하고 결과를 돌려받는 런처
    lateinit var launcher: ActivityResultLauncher<Intent>

    // 그림 리소스 (인덱스 0~8 = shape01~09)
    val drawableId = intArrayOf(
        R.drawable.shape01, R.drawable.shape02, R.drawable.shape03,
        R.drawable.shape04, R.drawable.shape05, R.drawable.shape06,
        R.drawable.shape07, R.drawable.shape08, R.drawable.shape09
    )
    // 결과 표시용 이미지뷰 3칸
    val imageviewId = intArrayOf(R.id.image01, R.id.image02, R.id.image03)
    var ivObj: Array<ImageView?> = arrayOfNulls<ImageView>(3)

    lateinit var rdObj1: RadioButton
    lateinit var rdObj2: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rdObj1 = findViewById(R.id.radioButton1)
        rdObj2 = findViewById(R.id.radioButton2)
        for (i in 0..2) ivObj[i] = findViewById(imageviewId[i])

        // ① 결과를 돌려받았을 때 할 일 등록
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // GameActivity가 보낸 선택 상태 배열 받기 (1 = 선택, 0 = 미선택)
                val resId = result.data?.getIntArrayExtra("resId") ?: IntArray(9)

                // 이전 결과 지우기
                for (i in 0..2) ivObj[i]?.setImageDrawable(null)

                // 선택한 그림을 결과 칸에 차례로 표시
                var num = 0
                for (i in 0..8) {
                    if (resId[i] == 1 && num < 3) {
                        ivObj[num]?.setImageResource(drawableId[i])
                        num++
                    }
                }

                // 정답 여부 확인
                var flag = true
                for (i in 0..8) {
                    if (resId[i] == 1) {
                        // 원: shape01~03(인덱스 0~2)만 정답
                        if (rdObj1.isChecked && i > 2) flag = false
                        // 사각형: shape04~06(인덱스 3~5)만 정답
                        if (rdObj2.isChecked && (i <= 2 || i >= 6)) flag = false
                    }
                }

                // 결과 표시
                val layoutResult: LinearLayout = findViewById(R.id.layoutResult)
                layoutResult.visibility = View.VISIBLE
                val tvObj: TextView = findViewById(R.id.textViewResult)
                tvObj.visibility = View.VISIBLE
                tvObj.text = if (flag) "맞았습니다" else "틀렸습니다"
            }
        }

        // ② 시작하기: 선택한 모양을 담아 GameActivity 실행
        val btnObj: Button = findViewById(R.id.button)
        btnObj.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            if (rdObj1.isChecked) intent.putExtra("shape", 1)       // 원
            else if (rdObj2.isChecked) intent.putExtra("shape", 2)  // 사각형
            launcher.launch(intent)   // startActivity 대신 launch → 결과를 돌려받음
        }
    }
}