package com.example.mp0902   // ← 기존 첫 줄 유지

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var gridview: GridView
    lateinit var adapter: MyAdapter
    val catsList = ArrayList<MyItem>()
    var selectItemPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ① 데이터
        catsList.add(MyItem("아비시니안", "3~5kg", R.drawable.abyssinian))
        catsList.add(MyItem("샴", "3.6~5.4kg", R.drawable.siamese))
        catsList.add(MyItem("앙고라", "3.7~7kg", R.drawable.angora))
        catsList.add(MyItem("랙돌", "5~9kg", R.drawable.regdol))
        catsList.add(MyItem("뱅갈", "5~8kg", R.drawable.bengal))
        catsList.add(MyItem("스핑크스", "4~5kg", R.drawable.sphinx))
        catsList.add(MyItem("페르시안", "3.6~5.4kg", R.drawable.persian))

        // ② 어댑터 연결
        gridview = findViewById(R.id.gridview)
        adapter = MyAdapter(catsList)
        gridview.adapter = adapter

        // ③ 칸 클릭
        gridview.setOnItemClickListener { parent, view, position, id ->
            selectItemPos = position
            Toast.makeText(this, catsList[position].name, Toast.LENGTH_SHORT).show()
        }

        // 추가
        findViewById<Button>(R.id.button1).setOnClickListener {
            catsList.add(MyItem("러시안블루${catsList.size + 1}", "3.5~5kg", R.drawable.russianblue))
            adapter.notifyDataSetChanged()
            gridview.smoothScrollToPosition(catsList.size - 1)   // 추가한 칸으로 스크롤
        }

        // 수정 / 삭제
        findViewById<Button>(R.id.button2).setOnClickListener { showDialog(isModify = true) }
        findViewById<Button>(R.id.button3).setOnClickListener { showDialog(isModify = false) }
    }

    // 수정/삭제 공용 대화상자 (9-1과 같음)
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
                val weight = dialogView.findViewById<EditText>(R.id.editWeight).text.toString()
                if (weight.isEmpty()) {
                    Toast.makeText(this, "몸무게를 입력하세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                catsList[pos].weight = "몸무게 ${weight}kg"
            } else {
                catsList.removeAt(pos)
                gridview.clearChoices()
                selectItemPos = -1
            }
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        buttonNo.setOnClickListener { dialog.dismiss() }
    }
}