package com.example.stackexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.dataclasses.Question
import androidx.recyclerview.widget.DividerItemDecoration




class MainActivity : AppCompatActivity() {
    var count = 0
    val questions: MutableList<Question> = arrayListOf()
    lateinit var questionList: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Nested ScrollView
        val nestedSV = findViewById<NestedScrollView>(R.id.nestedSV)
        var loadingAnimation = findViewById<ProgressBar>(R.id.loadingAnimation)

        // Obtains the RecyclerView to display the questions
        questionList = findViewById(R.id.questionList)

        questions.add(Question(2, "Question2", "Comment2"))
        questions.add(Question(3, "Question3", "Comment3"))
        questions.add(Question(4, "Question4", "Comment4"))
        questions.add(Question(1, "Question1", "Comment1"))
        questions.add(Question(2, "Question2", "Comment2"))
        questions.add(Question(3, "Question3", "Comment3"))
        questions.add(Question(4, "Question4", "Comment4"))
        questions.add(Question(1, "Question1", "Comment1"))
        questions.add(Question(2, "Question2", "Comment2"))
        questions.add(Question(3, "Question3", "Comment3"))
        questions.add(Question(4, "Question4", "Comment4"))
        questionList.adapter = QuestionAdapter(questions)

        layoutManager = LinearLayoutManager(this)
        questionList.layoutManager = layoutManager

        // Retrieves the first 10 items
        getData()

        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                count++
                // on below line we are making our progress bar visible.
                loadingAnimation.setVisibility(View.VISIBLE)
                if (count < 20) {
                    // on below line we are again calling
                    // a method to load data in our array list.
                    getData()
                }
            }
        })
    }

    fun getData(){
        // Add data
        questions.add(Question(1, "Question1", "Comment1"))


        // Binds the obtained data to the RecyclerView
        // Assigns Linear Layout as the layout manager for the RecyclerView
        questionList.adapter = QuestionAdapter(questions)
        val dividerItemDecoration = DividerItemDecoration(
            questionList.getContext(),
            layoutManager.getOrientation()
        )

        // Displays dividers
        questionList.addItemDecoration(dividerItemDecoration)
    }
}