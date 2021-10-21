package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

//ADAPTER
//prev declaration: class NewsListAdapter(private val items: ArrayList<String>, private val listener: NewsItemClicked):
//    RecyclerView.Adapter<NewsViewHolder>()

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    //Below three functions needed/ suggested on default for adapter class
    //1: called every time new view appears on screen. It returns a NewsViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        //Layout inflater converts xml to view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        //we get position of item in onClickListener block in OnItemClicked fun using view holder
        val viewHolder = NewsViewHolder(view)

        //now adding on click functionality to the item
        //we need to create the on click process in its own activity, not in adapter
        //to do so, we make a callback to that on click activity using interface
        view.setOnClickListener {
            //the item at given position(from view holder) will be passed to onItemClicked fun
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        //return NewsViewHolder(view)
        return viewHolder
    }
    //2: binds data into the holder
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }
    //3: called once to know total number of views
    override fun getItemCount(): Int {
        return items.size
    }

    //Made function to pass news items read from url to adapter
    fun updateNews(updatedNews: ArrayList<News>) {
        //first clear then add news list, data to be passed in adapter
        items.clear()
        items.addAll(updatedNews)

        //will call above 3 functions again, notify adapter
        notifyDataSetChanged()
    }
}
//View Holder
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked{
    fun onItemClicked(item:News)
    //fun onItemClicked(item:String)
}