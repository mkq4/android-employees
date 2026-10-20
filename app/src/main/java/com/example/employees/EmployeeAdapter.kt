package com.example.employees

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class EmployeeAdapter(context: Context, employees: ArrayList<Employee>) :
    ArrayAdapter<Employee>(context, 0, employees) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false)

        val employee = getItem(position)!!

        val imagePhoto = view.findViewById<ImageView>(R.id.imagePhoto)
        val textName = view.findViewById<TextView>(R.id.textName)
        val textAge = view.findViewById<TextView>(R.id.textAge)

        textName.text = employee.name
        textAge.text = "Возраст: ${employee.age}"

        if (employee.photo != null) {
            val bitmap = BitmapFactory.decodeByteArray(employee.photo, 0, employee.photo.size)
            imagePhoto.setImageBitmap(bitmap)
        } else {
            imagePhoto.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        return view
    }
}
