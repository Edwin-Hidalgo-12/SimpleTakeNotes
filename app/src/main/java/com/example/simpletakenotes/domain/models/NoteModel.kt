package com.example.simpletakenotes.domain.models

import java.util.Date

class NoteModel(
    val id: Int,
    val title: String,
    val content: String,
    val created: Date
)