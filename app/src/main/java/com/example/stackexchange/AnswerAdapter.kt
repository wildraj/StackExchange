package com.example.stackexchange

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.dataclasses.Answer
import com.example.stackexchange.dataclasses.Question

class AnswerAdapter(private val dataSet: MutableList<Answer>): RecyclerView.Adapter<AnswerAdapter.ViewHolder>()  {
    // Retrives the elements of the view and assigns them
    var onItemClick: ((Answer, Int) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val comment: TextView
        init {
            comment = view.findViewById(R.id.answer)
            view.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition], adapterPosition)
                if(dataSet[adapterPosition].isAccepted){
                    view.setBackgroundColor(Color.parseColor("#00FF00"))
                } else {
                    view.setBackgroundColor(Color.parseColor("#FF0000"))
                }
            }
        }
    }

    // Uses the question view as the view of a row for the RecyclerView
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.answer, viewGroup, false)
        return ViewHolder(view)
    }

    // Binds data to each of the rows
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.comment.text = dataSet[position].comment
    }

    // Sets the size of the data
    override fun getItemCount(): Int {
        return dataSet.size
    }
}