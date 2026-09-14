package com.example.simpletakenotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import com.example.simpletakenotes.mvcExample.NotesDB

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.bottom, systemBars.bottom)
            insets
        }

        // ---- Ejemplo de POO ----
        val pantallaSamsung = Pantalla(marca = "Samsung", tamañoPulgadas = 21.0, id = 1, priceBuy = 100.0, priceSale = 175.0)
        val pantallaAOC = Pantalla(tamañoPulgadas = 24.0, id = 2, marca = "AOC", priceBuy = 15.0, priceSale = 100.0)
        val teclado1 = Teclado(marca = "Delius", priceBuy = 80.0, priceSale = 120.0, tipoTeclado = "Español")

        println(pantallaSamsung.marca)
        println(pantallaAOC)
        println(teclado1)
        pantallaAOC.showInfo()
        teclado1.showInfo()
        println(pantallaAOC is Componente)  // true
        println(teclado1 is Componente)     // true
        println(pantallaAOC is Int)         // false

        // ---- Ejemplo MVC ----
        val notesDB = NotesDB()
        val etNote = findViewById<EditText>(R.id.etNote)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val texto = etNote.text.toString()
            if (texto.isNotBlank()) {
                notesDB.notes.add(texto)
                etNote.text.clear()
                println(notesDB.notes)
            }
        }
    }
}