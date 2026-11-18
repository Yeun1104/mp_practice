package com.example.mp1004

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 가계부 DB(HKBook.db)를 만들고 읽고 쓰는 도우미
class DBHelper(context: Context) : SQLiteOpenHelper(context, "HKBook.db", null, 1) {

    // 앱을 처음 실행할 때 한 번: 테이블 생성
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS HKBook(" +
                "SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +   // 번호 (자동 증가)
                "CATEGORY TEXT, " +                            // 수입/지출
                "PURPOSE TEXT, " +                             // 용도
                "DATE TEXT, " +                                // 날짜 (yyyy-MM-dd)
                "MONEY INTEGER, " +                            // 금액
                "MEMO TEXT)"                                   // 메모
        db.execSQL(sql)
    }

    // DB 버전이 바뀔 때 (지금은 사용 안 함)
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    // 1건 저장 (INSERT)
    fun insert(vo: HouseKeeping) {
        val values = ContentValues().apply {
            put("CATEGORY", vo.category)
            put("PURPOSE", vo.purpose)
            put("DATE", vo.date)
            put("MONEY", vo.money)
            put("MEMO", vo.memo)
        }
        writableDatabase.insert("HKBook", null, values)
    }

    // 기간 검색 (SELECT ... WHERE DATE BETWEEN from AND to)
    fun findTime(fromDate: String, toDate: String): ArrayList<HouseKeeping> {
        val sql = "SELECT SEQ, CATEGORY, PURPOSE, DATE, MONEY, MEMO FROM HKBook " +
                "WHERE DATE BETWEEN ? AND ? ORDER BY DATE"
        val list = ArrayList<HouseKeeping>()
        readableDatabase.rawQuery(sql, arrayOf(fromDate, toDate)).use { result ->
            while (result.moveToNext()) {   // 한 줄씩 읽기
                list.add(
                    HouseKeeping(
                        result.getInt(0), result.getString(1), result.getString(2),
                        result.getString(3), result.getInt(4), result.getString(5)
                    )
                )
            }
        }
        return list
    }
}
