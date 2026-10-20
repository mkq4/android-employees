package com.example.employees

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "employees.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE employees (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, photo BLOB)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS employees")
        onCreate(db)
    }

    fun addEmployee(name: String, age: Int, photo: ByteArray?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("age", age)
        values.put("photo", photo)
        db.insert("employees", null, values)
        db.close()
    }

    fun getAllEmployees(): ArrayList<Employee> {
        val list = ArrayList<Employee>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM employees", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val age = cursor.getInt(2)
            val photo = cursor.getBlob(3)
            list.add(Employee(id, name, age, photo))
        }

        cursor.close()
        db.close()
        return list
    }

    fun deleteEmployee(id: Int) {
        val db = writableDatabase
        db.delete("employees", "id = ?", arrayOf(id.toString()))
        db.close()
    }
}
