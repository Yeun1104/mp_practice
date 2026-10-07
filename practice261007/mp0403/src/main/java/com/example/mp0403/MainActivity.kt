package com.example.mp0403

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //캐릭터 위에 겹쳐지는 아이템
    lateinit var ivObjCrown: ImageView
    lateinit var ivObjNecklace: ImageView
    lateinit var ivObjDress: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivObjCrown = findViewById(R.id.imageViewObjCrown)
        ivObjNecklace = findViewById(R.id.imageViewObjNecklace)
        ivObjDress = findViewById(R.id.imageViewObjDress)

        //처음엔 아무것도 안 입은 상태
        resetCharacter()
    }

    //캐릭터 초기화: 아이템 모두 숨기기
    fun resetCharacter() {
        ivObjCrown.setVisibility(View.INVISIBLE)
        ivObjNecklace.setVisibility(View.INVISIBLE)
        ivObjDress.setVisibility(View.INVISIBLE)
    }

    //아이템을 선택했을 때 호출되는 메서드
    fun onClickChoice(view: View) {
        when (view.getId()) {

            //드레스를 선택한 경우
            R.id.imageViewDress1 -> {
                ivObjDress.setVisibility(View.VISIBLE)
                ivObjDress.setImageResource(R.drawable.dress1)
            }
            R.id.imageViewDress2 -> {
                ivObjDress.setVisibility(View.VISIBLE)
                ivObjDress.setImageResource(R.drawable.dress2)
            }
            R.id.imageViewDress3 -> {
                ivObjDress.setVisibility(View.VISIBLE)
                ivObjDress.setImageResource(R.drawable.dress3)
            }

            //왕관을 선택한 경우
            R.id.imageViewCrown1 -> {
                ivObjCrown.setVisibility(View.VISIBLE)
                ivObjCrown.setImageResource(R.drawable.crown1)
            }
            R.id.imageViewCrown2 -> {
                ivObjCrown.setVisibility(View.VISIBLE)
                ivObjCrown.setImageResource(R.drawable.crown2)
            }

            //목걸이를 선택한 경우
            R.id.imageViewNecklace1 -> {
                ivObjNecklace.setVisibility(View.VISIBLE)
                ivObjNecklace.setImageResource(R.drawable.necklace1)
            }
            R.id.imageViewNecklace2 -> {
                ivObjNecklace.setVisibility(View.VISIBLE)
                ivObjNecklace.setImageResource(R.drawable.necklace2)
            }

            //다시하기
            R.id.buttonReset -> resetCharacter()
        }
    }
}