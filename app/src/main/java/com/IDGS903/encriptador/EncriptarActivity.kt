package com.IDGS903.encriptador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject

class EncriptarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_encriptar)

        val btnE = findViewById<Button>(R.id.btn1)
        val btnS = findViewById<Button>(R.id.btn2)
        val txt = findViewById<EditText>(R.id.txtMensaje)
        val num = findViewById<EditText>(R.id.txtNumero)

        btnE.setOnClickListener {

            if (num.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numero = Integer.parseInt(num.text.toString())
            val mensaje = txt.text.toString()

            if (numero <= 0){
                Toast.makeText(this, "El número debe de ser mayor a 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevo = JSONObject()

            nuevo.put("id", System.currentTimeMillis())
            nuevo.put("mensaje", mensaje)
            nuevo.put("numero", numero)
            nuevo.put("encriptado", encriptar(mensaje, numero))

            val arreglo = try {

                val texto = openFileInput("msj.json")
                    .bufferedReader()
                    .useLines {
                        it.joinToString("")
                    }
                JSONArray(texto)
            } catch (e: Exception) {
                JSONArray()
            }

            arreglo.put(nuevo)
            openFileOutput("msj.json", MODE_PRIVATE).use {
                it.write(arreglo.toString().toByteArray())
            }
            Toast.makeText(this,"Texto encriptado y guardado",Toast.LENGTH_SHORT).show()
        }
        btnS.setOnClickListener { finish() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun encriptar(texto: String, desplazamiento: Int): String {

        val resultado = StringBuilder()

        for (char in texto) {

            if (char.isLetter()) {

                val base = if (char.isUpperCase()) 'A' else 'a'

                val offset = (char - base + desplazamiento) % 26

                val posicion = if (offset < 0) offset + 26 else offset

                resultado.append((base.code + posicion).toChar())

            } else {

                resultado.append(char)
            }
        }

        return resultado.toString()
    }
}