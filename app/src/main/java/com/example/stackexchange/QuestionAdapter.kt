package com.example.stackexchange

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.dataclasses.Question

class QuestionAdapter(private val dataSet: MutableList<Question>): RecyclerView.Adapter<QuestionAdapter.ViewHolder>()  {
    // Retrives the elements of the view and assigns them
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView
        val comment: TextView
        init {
            title = view.findViewById(R.id.questionTitle)
            comment = view.findViewById(R.id.questionComment)
        }
    }

    // Uses the question view as the view of a row for the RecyclerView
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.question, viewGroup, false)
        return ViewHolder(view)
    }

    // Binds data to each of the rows
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].title
        viewHolder.comment.text = dataSet[position].comment
    }

    // Sets the size of the data
    override fun getItemCount(): Int {
        return dataSet.size
    }
}