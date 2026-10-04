package com.example.simpletakenotes.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletakenotes.data.NotesDatabase
import com.example.simpletakenotes.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NotesAdapter

    // Escucha: navega a otra pantalla y espera una respuesta de vuelta
    private val formLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Solo entramos aquí con datos si la otra pantalla devolvió RESULT_OK
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("title")
            val content = data?.getStringExtra("content")
            Log.d("NotesActivity", "Recibido -> título: $title | contenido: $content")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        adapter = NotesAdapter(NotesDatabase().getFakeNotes())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        if (adapter.itemCount == 0) hideList() else showList()
    }

    private fun initListeners() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormNoteActivity::class.java)
            // PRUEBA: quita las dos barras de abajo para ver el modo "Actualización"
            // intent.putExtra("id", 1)
            formLauncher.launch(intent)
        }
    }

    private fun hideList() {
        binding.recyclerView.visibility = View.GONE
        binding.llMessage.visibility = View.VISIBLE
    }

    private fun showList() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.llMessage.visibility = View.GONE
    }
}