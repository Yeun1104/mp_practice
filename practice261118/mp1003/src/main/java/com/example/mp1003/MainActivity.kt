package com.example.mp1003

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var myPicture: ImageView
    private lateinit var tvNumber: TextView
    private var curNum = 0                          // 현재 이미지 번호
    private var imageFiles: List<File> = emptyList()  // 외부 저장소의 이미지 파일 목록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPrev: Button = findViewById(R.id.btnPrev)
        val btnNext: Button = findViewById(R.id.btnNext)
        tvNumber = findViewById(R.id.tvNumber)
        myPicture = findViewById(R.id.imvPicture)

        // ① 외부 저장소의 앱 전용 폴더: /sdcard/Android/data/com.example.mp1003/files/MyFileStorage
        val picturesDir = getExternalFilesDir("MyFileStorage")

        // (추가) 폴더가 비어 있으면 앱에 넣어둔 샘플 고양이 그림(assets)을 복사해 둠
        if (picturesDir != null) copySampleImagesIfEmpty(picturesDir)

        // ② jpg/png 파일만 이름순으로 가져오기
        imageFiles = picturesDir
            ?.listFiles { file -> file.name.endsWith(".jpg") || file.name.endsWith(".png") }
            ?.sortedBy { it.name }
            ?: emptyList()

        if (imageFiles.isEmpty()) {
            Toast.makeText(this, "이미지가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        showImage(curNum)

        // 이전 그림 (처음에서 누르면 마지막으로)
        btnPrev.setOnClickListener {
            curNum = if (curNum <= 0) imageFiles.size - 1 else curNum - 1
            showImage(curNum)
        }
        // 다음 그림 (마지막에서 누르면 처음으로)
        btnNext.setOnClickListener {
            curNum = if (curNum >= imageFiles.size - 1) 0 else curNum + 1
            showImage(curNum)
        }
    }

    // index번째 파일을 비트맵으로 읽어 화면에 표시
    private fun showImage(index: Int) {
        val imageFile = imageFiles[index]
        if (imageFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            myPicture.setImageBitmap(bitmap)
            tvNumber.text = "${index + 1}/${imageFiles.size}"
        } else {
            Toast.makeText(this, "이미지를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 외부 저장소 폴더에 이미지가 하나도 없을 때만 assets의 cat1~6.png를 복사
    private fun copySampleImagesIfEmpty(dir: File) {
        val hasImage = dir.listFiles()?.any { it.name.endsWith(".png") || it.name.endsWith(".jpg") } == true
        if (hasImage) return
        val samples = assets.list("")?.filter { it.endsWith(".png") } ?: return
        for (name in samples) {
            assets.open(name).use { input ->
                File(dir, name).outputStream().use { output -> input.copyTo(output) }
            }
        }
    }
}
