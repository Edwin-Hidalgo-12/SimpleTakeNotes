package com.example.simpletakenotes.presentation

import android.content.Intent
import android.os.Build
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
import com.example.simpletakenotes.domain.models.NoteModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NotesAdapter

    // Escucha: navega a otra pantalla y espera una respuesta de vuelta
    private val formLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data

            // Se lee como Parcelable (no como String), según la versión de Android
            val note: NoteModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra("note", NoteModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra("note")
            }

            // Puede no llegar nada, por eso se valida antes de usarla
            if (note != null) {
                note.id = adapter.itemCount + 1 // el siguiente número de la lista
                Log.d(
                    "NotesActivity",
                    "Insertando -> id: ${note.id} | título: ${note.title} | contenido: ${note.content}"
                )
                adapter.insertNote(note)
                binding.recyclerView.scrollToPosition(adapter.itemCount - 1) // baja hasta la nueva nota
                validateList()
            }
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
        // toMutableList(): el adapter ahora necesita una lista a la que se le pueda agregar
        adapter = NotesAdapter(NotesDatabase().getFakeNotes().toMutableList())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        validateList()
    }

    private fun initListeners() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormNoteActivity::class.java)
            // PRUEBA: quita las dos barras de abajo para ver el modo "Actualización"
            // intent.putExtra("id", 1)
            formLauncher.launch(intent)
        }
    }

    // Muestra la lista si hay notas, o el mensaje de "vacío" si no hay ninguna
    private fun validateList() {
        if (adapter.itemCount == 0) hideList() else showList()
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