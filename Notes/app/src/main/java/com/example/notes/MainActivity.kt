package com.example.notes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView stuff
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =NoteRVAdapter(this,this)

        recyclerView.adapter = adapter

        //ViewModel stuff
        //viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        //all notes is live data which can be observed. observe needs a lifecycleOwner
        viewModel.allNotes.observe(this, Observer { list->
            list?.let {
                adapter.updateList(it)
            }
        })
        // We were getting it as default var in observe function, we first made it list,
        //then we checked for it's nullability by ?. , and if not null, perform let part

    }

    //Delete button
    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
    }

    //Submit button
    fun submitData(view: View) {

        // Hide the keyboard.
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        val input: EditText =findViewById(R.id.input)
        val inputText = input.text.toString()
        if(inputText.isNotEmpty()) {
            viewModel.insertNote(Note(inputText))
            Toast.makeText(this, "$inputText Inserted", Toast.LENGTH_SHORT).show()
        }
    }
}