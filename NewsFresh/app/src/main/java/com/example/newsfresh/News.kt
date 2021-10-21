package com.example.newsfresh

//A data only class where we will store data from json object
//and make an array of this class
data class News (
    val title: String,
    val author: String,
    val url: String,
    val imageUrl: String
)