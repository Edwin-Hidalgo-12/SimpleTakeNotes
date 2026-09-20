package com.example.simpletakenotes.data

import com.example.simpletakenotes.domain.models.NoteModel
import java.util.Date

class NotesDatabase {

    fun getFakeNotes(): List<NoteModel> {
        return listOf(
            NoteModel(1, "Lista de compras", "Comprar leche, pan, huevos y fruta", Date()),
            NoteModel(2, "Ideas de proyectos", "App de recordatorios y gestor de gastos", Date()),
            NoteModel(3, "Recordatorio", "Llamar al doctor el viernes a las 10:00", Date()),
            NoteModel(4, "Tareas del día", "Terminar informe y revisar correos", Date()),
            NoteModel(5, "Frases", "La práctica hace al maestro", Date())
        )
    }
}