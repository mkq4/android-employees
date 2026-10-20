package com.example.employees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dbHelper: DBHelper
    private var employees = ArrayList<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)
        listView = findViewById(R.id.listView)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val employee = employees[position]
            AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Удалить сотрудника ${employee.name}?")
                .setPositiveButton("Да") { _, _ ->
                    dbHelper.deleteEmployee(employee.id)
                    loadEmployees()
                    Toast.makeText(this, "Сотрудник удалён", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Нет", null)
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadEmployees()
    }

    private fun loadEmployees() {
        employees = dbHelper.getAllEmployees()
        listView.adapter = EmployeeAdapter(this, employees)
    }
}
