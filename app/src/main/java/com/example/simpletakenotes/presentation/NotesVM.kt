package com.example.simpletakenotes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpletakenotes.data.NotesDatabase
import com.example.simpletakenotes.domain.models.NoteModel

class NotesVM : ViewModel() {

    // Lista real: privada, solo el ViewModel la modifica
    private val _notes: MutableList<NoteModel> =
        NotesDatabase().getFakeNotes().toMutableList()

    // Observable privado (se puede cambiar) con valor inicial: las notas falsas
    private val _notesObs = MutableLiveData<List<NoteModel>>(_notes.toList())

    // Observable público (solo lectura): la Activity puede observarlo, pero no cambiarlo
    val notesObs: LiveData<List<NoteModel>> = _notesObs

    fun insertNote(note: NoteModel) {
        note.id = _notes.size + 1
        _notes.add(note)
        // Asignar un nuevo valor = notificar a todos los que observan
        _notesObs.value = _notes.toList()
        Log.d("NotesVM", "Insertada -> id: ${note.id} | título: ${note.title} | total: ${_notes.size}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("NotesVM", "onCleared: el ViewModel se destruyó")
    }
}