package com.example.simpletakenotes.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simpletakenotes.databinding.ActivityFormNoteBinding

class FormNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFormNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Si llegan argumentos por el Intent, es una actualización; si no, es un registro
        val isUpdate = intent.extras != null
        setupUI(isUpdate)
        setupListeners()
    }

    private fun setupUI(isUpdate: Boolean) {
        if (isUpdate) {
            binding.linearLayoutUpdateButtons.visibility = View.VISIBLE
            binding.btnSaveNote.visibility = View.GONE
            binding.textViewTitle.text = "Actualización"
        } else {
            binding.linearLayoutUpdateButtons.visibility = View.GONE
            binding.btnSaveNote.visibility = View.VISIBLE
            binding.textViewTitle.text = "Registro"
        }
    }

    private fun isValidForm(): Boolean {
        val title = binding.editTextTitle.text.toString()
        val content = binding.editTextContent.text.toString()

        binding.textInputLayoutTitle.error =
            if (title.isBlank()) "Campo requerido" else null
        binding.textInputLayoutContent.error =
            if (content.isBlank()) "Campo requerido" else null

        return title.isNotBlank() && content.isNotBlank()
    }

    // Crea la "cajita" (Intent) con los datos y la devuelve a la pantalla anterior
    private fun returnResult() {
        val resultIntent = Intent().apply {
            putExtra("title", binding.editTextTitle.text.toString())
            putExtra("content", binding.editTextContent.text.toString())
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun setupListeners() {
        binding.btnSaveNote.setOnClickListener {
            if (isValidForm()) returnResult()
        }

        binding.btnUpdate.setOnClickListener {
            if (isValidForm()) returnResult()
        }

        binding.btnCancel.setOnClickListener {
            finish() // sin setResult: la pantalla anterior recibe RESULT_CANCELED
        }

        binding.imageViewBack.setOnClickListener {
            finish()
        }
    }
}