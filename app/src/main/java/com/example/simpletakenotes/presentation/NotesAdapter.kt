package com.example.simpletakenotes.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletakenotes.databinding.ItemNoteBinding
import com.example.simpletakenotes.domain.models.NoteModel

class NotesAdapter(
    private val items: List<NoteModel>
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(
        val itemNoteBinding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(itemNoteBinding.root)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemNoteBinding = ItemNoteBinding.inflate(layoutInflater, parent, false)
        return NotesViewHolder(itemNoteBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = items[position]
        holder.itemNoteBinding.textViewTitle.text = note.title
        holder.itemNoteBinding.textViewContent.text = note.content
    }
}