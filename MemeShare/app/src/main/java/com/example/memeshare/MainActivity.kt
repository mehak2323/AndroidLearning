package com.example.memeshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Run this function
        loadMeme()
    }

    private fun loadMeme(){

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                //textView.text = "Response is: ${response.substring(0, 500)}"
                Log.d("success request",${response.substring(0, 500)})
            },
            Response.ErrorListener {
                //textView.text = "That didn't work!"
                Log.d("error", it.localizedMessage)
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun shareMeme(view: View) {}
    fun nextMeme(view: View) {}
}