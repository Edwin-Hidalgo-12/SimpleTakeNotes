package com.example.simpletakenotes.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpletakenotes.databinding.ActivityNotesBinding
import com.example.simpletakenotes.domain.models.NoteModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: NotesAdapter

    private val vm: NotesVM by viewModels()

    // Escucha: navega a otra pantalla y espera una respuesta de vuelta
    private val formLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data

            val note: NoteModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra("note", NoteModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra("note")
            }

            if (note != null) {
                // Solo se avisa al ViewModel. La lista se actualiza sola gracias al observer.
                vm.insertNote(note)
                // post: espera a que la lista termine de dibujarse antes de bajar
                binding.recyclerView.post {
                    binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
                }
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
        initObservers()
    }

    private fun initRecyclerView() {
        // El adapter empieza vacío: los datos le llegan por el observer
        adapter = NotesAdapter(mutableListOf())
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun initListeners() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, FormNoteActivity::class.java)
            formLauncher.launch(intent)
        }
    }

    // La "viejita chismosa" del profe: está atenta a cada cambio de notesObs
    private fun initObservers() {
        // this = la Activity (LifecycleOwner): se observa mientras la Activity esté viva
        vm.notesObs.observe(this) { notes ->
            adapter.setNotes(notes)
            if (notes.isEmpty()) hideList() else showList()
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