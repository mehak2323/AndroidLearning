package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    //we need all notes from view model, used in activity
    val allNotes : LiveData<List<Note>>

    //to get all notes below init procedure
    //get all notes from repository, but it needs dao so get dao first

    private val repository : NoteRepository
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    //Coroutine's scope mein bg thread mein kaam karega below function
    //delete func below can only be called by coroutine or another suspend function
    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun insertNote(note:Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}