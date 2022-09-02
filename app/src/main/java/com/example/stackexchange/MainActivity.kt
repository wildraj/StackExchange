package com.example.stackexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.dataclasses.Question
import androidx.recyclerview.widget.DividerItemDecoration




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtains the RecyclerView to display the questions
        val questionList = findViewById<RecyclerView>(R.id.questionList)

        // Some Test Data
        val questions: MutableList<Question> = arrayListOf()
        questions.add(Question(1, "Question1", "Comment1"))
        questions.add(Question(2, "Question2", "Comment2"))
        questions.add(Question(3, "Question3", "Comment3"))
        questions.add(Question(4, "Question4", "Comment4"))

        // Assigns Linear Layout as the layout manager for the RecyclerView
        val layoutManager = LinearLayoutManager(this)
        questionList.layoutManager = layoutManager

        // Binds the obtained data to the RecyclerView
        questionList.adapter = QuestionAdapter(questions)
        val dividerItemDecoration = DividerItemDecoration(
            questionList.getContext(),
            layoutManager.getOrientation()
        )

        // Displays dividers
        questionList.addItemDecoration(dividerItemDecoration)
    }
}