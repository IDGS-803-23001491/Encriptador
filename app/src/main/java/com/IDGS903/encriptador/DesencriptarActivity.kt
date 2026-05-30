package com.IDGS903.encriptador

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONObject

class DesencriptarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_desencriptar)

        val btnD = findViewById<Button>(R.id.btn1)
        val btnS = findViewById<Button>(R.id.btn2)
        val num = findViewById<EditText>(R.id.txtNumero)
        val spinner = findViewById<Spinner>(R.id.spnMensajes)
        val txtO = findViewById<TextView>(R.id.txtO)
        val txtD = findViewById<TextView>(R.id.txtD)
        val listaObjetos = mutableListOf<JSONObject>()
        val listaMensajes = mutableListOf<String>()

        val texto = openFileInput("msj.json")
            .bufferedReader()
            .useLines {
                it.joinToString("")
            }

        val arreglo = JSONArray(texto)

        for (i in 0 until arreglo.length()) {

            val objeto = arreglo.getJSONObject(i)

            listaObjetos.add(objeto)

            listaMensajes.add(
                objeto.getString("encriptado")
            )
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaMensajes
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinner.adapter = adapter

        btnD.setOnClickListener {

            if (num.text.toString().isEmpty()) {
                Toast.makeText(this, "Ingrese un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numero = Integer.parseInt(num.text.toString())
            val mensaje = spinner.selectedItem.toString()

            if (numero <= 0){
                Toast.makeText(this, "El número debe de ser mayor a 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val posicion = spinner.selectedItemPosition
            val objeto = listaObjetos[posicion]
            val original = objeto.getString("mensaje")
            val nuevo = encriptar(mensaje,(numero * -1))

            txtO.setText("Original: " + original)
            txtD.setText("Desencriptado: " + nuevo)
            if (original == nuevo){
                Toast.makeText(this, "Desencriptación correcta", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Desencriptación incorrecta", Toast.LENGTH_SHORT).show()
            }

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