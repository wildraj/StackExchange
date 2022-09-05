package com.example.stackexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchange.dataclasses.Question
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley;
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    var count = 0
    val questions: MutableList<Question> = arrayListOf()
    lateinit var questionList: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Nested ScrollView
        val nestedSV = findViewById<NestedScrollView>(R.id.nestedSV)
        var loadingAnimation = findViewById<ProgressBar>(R.id.loadingAnimation)

        // Obtains the RecyclerView to display the questions
        questionList = findViewById(R.id.questionList)
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
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.stackexchange.com/search/advanced?page="+page+"&pagesize=10&sort=activity&order=desc&accepted=True&answers=2&site=stackoverflow"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val items = response.getJSONArray("items")
                for (i in 0..items.length() - 1) {
                    try {
                        val obj = items.getJSONObject(i)
                        questions.add(
                            Question(
                                obj.getInt("question_id"),
                                obj.getString("title")
                            )
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                // Binds the obtained data to the RecyclerView
                // Assigns Linear Layout as the layout manager for the RecyclerView
                val adapter = QuestionAdapter(questions)
                adapter.onItemClick = { question ->
                    // Get to new page here
                    val intent = Intent(this@MainActivity, AnswerActivity::class.java)
                    intent.putExtra("question", question)
                    startActivity(intent)
                }
                questionList.adapter = adapter
                val dividerItemDecoration = DividerItemDecoration(
                    questionList.getContext(),
                    layoutManager.getOrientation()
                )

                // Displays dividers
                questionList.addItemDecoration(dividerItemDecoration)
                page++
            },
            { error ->
                println("===============")
                println(error)
            }
        )
        queue.add(request)
    }
}