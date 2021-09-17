package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
//import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Run this function
        loadMeme()
    }

    private fun loadMeme(){

        imageProgressBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        //Queue is used to store process for the api library to get data or load from cache,etc
        //removing volley queue specific to this function and replace with singleton queue
        //val queue = Volley.newRequestQueue(this)
        //val url = "https://www.google.com"
        val url = "https://meme-api.herokuapp.com/gimme"

        //get json object request and load image using glide library
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                //textView.text = "Response: %s".format(response.toString())
                currentImageUrl = response.getString("url")

                //we need to remove progress bar once image loads, so using request listener anonymous object
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageProgressBar.visibility = View.GONE
                        return false
                    }

                } ).into(MemeImageView)
            },
            { //error ->
                //Log.d("error", it.localizedMessage)
            }
        )

        // Request a string response from the provided URL.
        //multiple requests, we using string. get method used, if failed error below.
        //string requests- get, post, put (backend update)
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//                // Display the first 500 characters of the response string.
//                //textView.text = "Response is: ${response.substring(0, 500)}"
//                Log.d("success request", response.substring(0, 500))
//            },
//            {
//                //textView.text = "That didn't work!"
//                Log.d("error", it.localizedMessage)
//            }
//        )

        // Add the request to the RequestQueue. becomes invalid if using singleton
        //queue.add(jsonObjectRequest)

        //using singleton class ensure single instance of volley for all app
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        //intents used to change process
        val intent= Intent(Intent.ACTION_SEND)
        //defining what type of thing we have, to suggest those apps for sharing, like pdf, text, image, etc
        intent.type = "text/plain"
        //extra text while sharing
        intent.putExtra(Intent.EXTRA_TEXT, "Hi, check out this meme from Reddit $currentImageUrl")

        //gives us sharing app options
        val chooser = Intent.createChooser(intent, "Share this meme using: ")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}