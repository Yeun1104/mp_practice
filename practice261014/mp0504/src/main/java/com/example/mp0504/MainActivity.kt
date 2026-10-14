package com.example.mp0504

import android.os.Bundle
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var ivBg: ImageView
    lateinit var ivLights: Array<ImageView>
    lateinit var toggle: ToggleButton

    //조명 이미지 (꺼짐 / 켜짐)
    val lightsOff = intArrayOf(R.drawable.light11, R.drawable.light12, R.drawable.light13)
    val lightsOn  = intArrayOf(R.drawable.light21, R.drawable.light22, R.drawable.light23)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivBg = findViewById(R.id.imageViewBg)
        ivLights = arrayOf(
            findViewById(R.id.imageViewLight1),
            findViewById(R.id.imageViewLight2),
            findViewById(R.id.imageViewLight3)
        )
        toggle = findViewById(R.id.toggleButton)

        //토글 상태가 바뀔 때 호출 (isChecked: true = 켜짐)
        toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                ivBg.setImageResource(R.drawable.bg2)
                for (i in ivLights.indices) ivLights[i].setImageResource(lightsOn[i])
                toggle.setBackgroundResource(R.drawable.on)
            } else {
                ivBg.setImageResource(R.drawable.bg1)
                for (i in ivLights.indices) ivLights[i].setImageResource(lightsOff[i])
                toggle.setBackgroundResource(R.drawable.off)
            }
        }
    }
}