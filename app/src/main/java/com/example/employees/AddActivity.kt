package com.example.employees

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream

class AddActivity : AppCompatActivity() {

    private lateinit var imagePhoto: ImageView
    private lateinit var editName: EditText
    private lateinit var editAge: EditText
    private var selectedBitmap: Bitmap? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            loadImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        imagePhoto = findViewById(R.id.imagePhoto)
        editName = findViewById(R.id.editName)
        editAge = findViewById(R.id.editAge)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        imagePhoto.setOnClickListener {
            pickImage.launch("image/*")
        }

        buttonSave.setOnClickListener {
            saveEmployee()
        }
    }

    private fun loadImage(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            selectedBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true)
            imagePhoto.setImageBitmap(selectedBitmap)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось загрузить фото", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEmployee() {
        val name = editName.text.toString().trim()
        val ageText = editAge.text.toString().trim()

        if (name.isEmpty() || ageText.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toInt()

        var photo: ByteArray? = null
        if (selectedBitmap != null) {
            val stream = ByteArrayOutputStream()
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            photo = stream.toByteArray()
        }

        val dbHelper = DBHelper(this)
        dbHelper.addEmployee(name, age, photo)

        Toast.makeText(this, "Сотрудник добавлен", Toast.LENGTH_SHORT).show()
        finish()
    }
}
