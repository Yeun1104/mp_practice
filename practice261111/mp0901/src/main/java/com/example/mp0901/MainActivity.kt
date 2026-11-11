package com.example.mp0901

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var listview: ListView
    lateinit var adapter: MyAdapter
    val catsList = ArrayList<MyItem>()   // 데이터 목록
    var selectItemPos = -1               // 선택한 줄 위치 (-1 = 선택 없음)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ① 데이터 준비
        catsList.add(MyItem("아비시니안", "활발하고 온순한 성격", "3~5kg", R.drawable.abyssinian))
        catsList.add(MyItem("샴", "놀기 좋아하고 온순한 성격", "3.6~5.4kg", R.drawable.siamese))
        catsList.add(MyItem("앙고라", "예민하고 상냥한 성격", "3.7~7kg", R.drawable.angora))
        catsList.add(MyItem("랙돌", "느긋하고 온순한 성격", "5~9kg", R.drawable.regdol))
        catsList.add(MyItem("뱅갈", "충성심 강하고 상냥한 성격", "5~8kg", R.drawable.bengal))
        catsList.add(MyItem("스핑크스", "활발하고 사교성 좋은 성격", "4~5kg", R.drawable.sphinx))
        catsList.add(MyItem("페르시안", "조용하고 온순한 성격", "3.6~5.4kg", R.drawable.persian))

        // ② 어댑터를 만들어 리스트 뷰에 연결
        listview = findViewById(R.id.listview)
        adapter = MyAdapter(catsList)
        listview.adapter = adapter

        // ③ 줄 클릭: 위치 기억 + 토스트
        listview.setOnItemClickListener { parent, view, position, id ->
            selectItemPos = position
            val item = catsList[position]
            Toast.makeText(this, "${item.name} ${item.character}", Toast.LENGTH_SHORT).show()
        }

        // 추가: 맨 끝에 러시안블루 추가
        findViewById<Button>(R.id.button1).setOnClickListener {
            catsList.add(MyItem("러시안블루${catsList.size + 1}", "온순하고 애교 많은 성격", "3.5~5kg", R.drawable.russianblue))
            adapter.notifyDataSetChanged()   // ★ 데이터가 바뀌었으니 화면 다시 그려라
        }

        // 수정 / 삭제
        findViewById<Button>(R.id.button2).setOnClickListener { showDialog(isModify = true) }
        findViewById<Button>(R.id.button3).setOnClickListener { showDialog(isModify = false) }
    }

    // 수정/삭제 공용 대화상자
    fun showDialog(isModify: Boolean) {
        val pos = selectItemPos
        if (pos < 0 || pos >= catsList.size) {
            Toast.makeText(this, "항목을 선택하세요", Toast.LENGTH_SHORT).show()
            return
        }

        val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).show()

        dialogView.findViewById<TextView>(R.id.textView).text = "고양이 : ${catsList[pos].name}"
        dialogView.findViewById<LinearLayout>(R.id.layout_modify).isVisible = isModify
        dialogView.findViewById<LinearLayout>(R.id.layout_delete).isVisible = !isModify

        val buttonYes: Button = dialogView.findViewById(R.id.button_yes)
        val buttonNo: Button = dialogView.findViewById(R.id.button_no)
        buttonYes.text = if (isModify) "수정" else "예"
        buttonNo.text = if (isModify) "취소" else "아니오"

        buttonYes.setOnClickListener {
            if (isModify) {
                // 무게만 바꿔서 같은 자리에 다시 넣기
                val weight = dialogView.findViewById<EditText>(R.id.editWeight).text.toString()
                if (weight.isEmpty()) {
                    Toast.makeText(this, "몸무게를 입력하세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                catsList[pos].weight = "몸무게 ${weight}kg"
            } else {
                catsList.removeAt(pos)
                listview.clearChoices()   // 노란 선택 표시 지우기
                selectItemPos = -1        // 선택 위치도 초기화
            }
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        buttonNo.setOnClickListener { dialog.dismiss() }
    }
}