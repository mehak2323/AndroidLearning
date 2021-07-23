package com.example.birthdaycard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class BirthdayCardActivity : AppCompatActivity() {
    /* companion object, like static variables, to make sure name match with id */
    companion object {
        const val NAME_EXTRA="name_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_birthday_card)

        /* take value of name with id "name" and assign to variable here */
        //val name = intent.getStringExtra("name") //without using companion object
        val name = intent.getStringExtra("NAME_EXTRA")

        val cardNameText = findViewById<TextView>(R.id.cardNameText)
        cardNameText.text= "Happy Birthday\n $name!"
    }
}