package com.example.simpletakenotes.data

import com.example.simpletakenotes.domain.models.NoteModel

class NotesDatabase {

    fun getFakeNotes(): List<NoteModel> {
        return listOf(
            NoteModel(id = 1, title = "Lista de compras", content = "Comprar leche, pan, huevos y fruta"),
            NoteModel(id = 2, title = "Ideas de proyectos", content = "App de recordatorios y gestor de gastos"),
            NoteModel(id = 3, title = "Recordatorio", content = "Llamar al doctor el viernes a las 10:00"),
            NoteModel(id = 4, title = "Tareas del día", content = "Terminar informe y revisar correos"),
            NoteModel(id = 5, title = "Frases", content = "La práctica hace al maestro")
        )
    }
}