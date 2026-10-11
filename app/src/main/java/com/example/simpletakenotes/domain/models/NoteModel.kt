package com.example.simpletakenotes.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NoteModel(
    var id: Int? = null,
    val title: String,
    val content: String
) : Parcelable