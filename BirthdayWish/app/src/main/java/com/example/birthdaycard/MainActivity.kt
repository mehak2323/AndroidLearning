package com.example.birthdaycard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /* Function called after button on click*/
    fun createBirthdayCard(view: View) {

        /*To use id of input text from xml, findViewById*/
        val nameInput = findViewById<EditText>(R.id.nameInput)
        /* Assign value of nameInput input text to name after convert to string*/
        val name = nameInput.editableText.toString()

        Toast.makeText(this, "Name is $name", Toast.LENGTH_LONG).show()

        /* Intent helps change activity, from this(main activity), to name of next activity */
        val intent = Intent(this, BirthdayCardActivity::class.java )
        /* Add our name input to intent bundle which is passed on to next activity */
        intent.putExtra("NAME_EXTRA",name) //key, value
        startActivity(intent)

    }
}