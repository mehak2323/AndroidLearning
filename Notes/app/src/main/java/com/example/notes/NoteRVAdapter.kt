package com.example.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(private val context: Context, private val listener: INoteRVAdapter) : RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.findViewById(R.id.text)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        //layout inflater which uses context received from adapter constructor to return viewholder
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,
            parent, false) )
        viewHolder.deleteButton.setOnClickListener{
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.textView.text = currentNote.text
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        //Delete prev, add current
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged() //update recyclerview
    }
}

interface INoteRVAdapter {
    fun onItemClicked(note : Note)
}