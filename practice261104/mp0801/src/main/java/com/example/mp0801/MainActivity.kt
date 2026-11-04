package com.example.mp0801

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var btnPizzaObj: Button
    lateinit var btnToppingObj: Button
    lateinit var btnOrderObj: Button
    lateinit var ivBgObj: ImageView
    lateinit var ivPizzaObj: ImageView
    lateinit var tvPizzaObj: TextView
    lateinit var tvToppingObj: TextView
    lateinit var ivDeliveryObj: ImageView
    lateinit var tvDeliveryObj: TextView

    // 피자 / 토핑 이름과 이미지 (순서 맞춤)
    val pizzaNames = arrayOf("마르게리타 피자", "불고기 피자", "양송이 피자", "하와이안 피자")
    val drawablePizza = intArrayOf(R.drawable.pizza1, R.drawable.pizza2, R.drawable.pizza3, R.drawable.pizza4)
    val toppingNames = arrayOf("양송이", "불고기", "바질", "베이컨", "해물")
    val drawableTopping = intArrayOf(
        R.drawable.topping1, R.drawable.topping2, R.drawable.topping3,
        R.drawable.topping4, R.drawable.topping5
    )
    val imageViewTopping = intArrayOf(R.id.topping1, R.id.topping2, R.id.topping3, R.id.topping4, R.id.topping5)
    val ivToppingObj = arrayOfNulls<ImageView>(5)

    var oldNum = 0   // 마지막으로 확정한 피자 번호

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnPizzaObj = findViewById(R.id.button_pizza)
        btnToppingObj = findViewById(R.id.button_topping)
        btnOrderObj = findViewById(R.id.button_order)
        ivBgObj = findViewById(R.id.imageView_bg)
        ivPizzaObj = findViewById(R.id.pizza)
        tvPizzaObj = findViewById(R.id.textView_pizza)
        tvToppingObj = findViewById(R.id.textView_topping)
        ivDeliveryObj = findViewById(R.id.delivery)
        tvDeliveryObj = findViewById(R.id.textView_delivery)
        for (i in 0..4) ivToppingObj[i] = findViewById(imageViewTopping[i])

        // 피자를 고르기 전에는 토핑/주문 버튼 비활성화
        btnToppingObj.isEnabled = false
        btnOrderObj.isEnabled = false

        btnPizzaObj.setOnClickListener { showPizzaDialog() }
        btnToppingObj.setOnClickListener { showToppingDialog() }
        btnOrderObj.setOnClickListener { showOrderDialog() }
    }

    // ① 라디오 버튼 목록 대화상자: 피자 하나 고르기
    fun showPizzaDialog() {
        var selectNum = oldNum   // 대화상자를 열 때마다 마지막 선택으로 시작

        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_pizza)
        builder.setTitle("주문할 피자를 선택하세요")
        // setSingleChoiceItems(목록, 처음 체크할 위치, 항목 클릭 리스너)
        builder.setSingleChoiceItems(pizzaNames, oldNum) { dialog, which ->
            Toast.makeText(this, pizzaNames[which], Toast.LENGTH_SHORT).show()
            selectNum = which
        }
        builder.setPositiveButton("예") { dialog, which ->
            oldNum = selectNum
            ivBgObj.isVisible = false
            ivDeliveryObj.isVisible = false
            tvDeliveryObj.isVisible = false
            ivPizzaObj.isVisible = true
            tvPizzaObj.isVisible = true
            ivPizzaObj.setImageResource(drawablePizza[selectNum])
            tvPizzaObj.text = pizzaNames[selectNum]
            // 피자가 바뀌면 이전 토핑 초기화
            for (iv in ivToppingObj) iv?.isVisible = false
            tvToppingObj.text = ""
            btnToppingObj.isEnabled = true
            btnOrderObj.isEnabled = true
        }
        builder.setNegativeButton("아니오", null)   // null이면 그냥 닫힘
        builder.show()
    }

    // ② 체크박스 목록 대화상자: 토핑 여러 개 고르기
    fun showToppingDialog() {
        val checkedArray = booleanArrayOf(false, false, false, false, false)

        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_pizza)
        builder.setTitle("추가할 토핑을 선택하세요")
        // setMultiChoiceItems(목록, 체크 상태 배열, 클릭 리스너(위치, 체크 여부))
        builder.setMultiChoiceItems(toppingNames, checkedArray) { dialog, which, isChecked ->
            Toast.makeText(this, toppingNames[which], Toast.LENGTH_SHORT).show()
            checkedArray[which] = isChecked
        }
        builder.setPositiveButton("예") { dialog, which ->
            var str = "토핑추가 : "
            for (i in 0..4) {
                ivToppingObj[i]?.isVisible = checkedArray[i]
                if (checkedArray[i]) {
                    ivToppingObj[i]?.setImageResource(drawableTopping[i])
                    str += toppingNames[i] + " "
                }
            }
            tvToppingObj.isVisible = true
            tvToppingObj.text = str
        }
        builder.setNegativeButton("아니오", null)
        builder.show()
    }

    // ③ 메시지 대화상자: 주문 확인
    fun showOrderDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.icon_pizza)
        builder.setMessage("주문하겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            ivDeliveryObj.isVisible = true
            tvDeliveryObj.isVisible = true
        }
        builder.setNegativeButton("아니오", null)
        builder.show()
    }
}