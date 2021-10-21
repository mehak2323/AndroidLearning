package com.example.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
//import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    //m =member variable, accessible everywhere
    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add layout manager to recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()

        //Define adapter
        //when we add listener as this, should be NewsItemClicked type but found MainActivity
        //so to fix do Let MainActivity implement NewsItemClicked
        mAdapter = NewsListAdapter(this)
        //val adapter = NewsListAdapter(items, this)

        //Add adapter to recyclerView
        recyclerView.adapter = mAdapter

    }

    private fun fetchData(){

        val url = "https://newsdata.io/api/1/news?apikey=pub_18439cf5ff83c8fb268e241efa0b41470c59"
        //val url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=1f4a12d2698e432ea9cf18126dcc7acd"
        //val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=c995fe9c9ef84a20adceb1e0de673b3b"

        //make a request for json object
        val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET, url, null,
            {
                //we first get articles from the list given in api link
                val newsJsonArray = it.getJSONArray("articles")
                //make a list of type News to store content of each news from the link
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {
                // TODO: Handle error
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

        //Toast.makeText(this, "Clicked item is $item",Toast.LENGTH_LONG).show()
    }
}