package com.IDGS903.encriptador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnE = findViewById<Button>(R.id.btn1);
        val btnD = findViewById<Button>(R.id.btn2);
        val btnS = findViewById<Button>(R.id.btn3);

        btnE.setOnClickListener { navegarE() }
        btnD.setOnClickListener { navegarD() }
        btnS.setOnClickListener { finish() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun navegarE(){
        val intent = Intent(this, EncriptarActivity::class.java);
        startActivity(
            intent
        )
    }

    fun navegarD(){
        val intent = Intent(this, DesencriptarActivity::class.java);
        startActivity(
            intent
        )
    }

}