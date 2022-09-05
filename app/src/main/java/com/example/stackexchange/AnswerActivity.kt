package com.example.stackexchange

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.stackexchange.dataclasses.Answer
import com.example.stackexchange.dataclasses.Question
import org.json.JSONException
import org.jsoup.Jsoup

class AnswerActivity : AppCompatActivity() {
    private var answers: MutableList<Answer> = arrayListOf()
    private lateinit var question: Question
    lateinit var answerList: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var feedback: TextView
    var acceptedIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_page)
        question = intent.getSerializableExtra("question") as Question
        if(question != null){
            answerList = findViewById(R.id.answerList)
            layoutManager = LinearLayoutManager(this)
            answerList.layoutManager = layoutManager

            val qTitle = findViewById<TextView>(R.id.qTitle)
            qTitle.text = question.title
            feedback = findViewById(R.id.feedback)
            getData()
        }
    }

    fun getData(){
        // Add data
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.stackexchange.com/questions/"+question.questionId+"/answers?order=desc&site=stackoverflow&filter=!ao-)iqhlPTEH2L "
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                println(response)
                val items = response.getJSONArray("items")
                for (i in 0..items.length() - 1) {
                    try {
                        val obj = items.getJSONObject(i)
                        var comment = "No Comment"
                        if(obj.has("body")){
                            comment = Jsoup.parse(obj.getString("body")).text()
                        }
                        answers.add(
                            Answer(
                                obj.getInt("answer_id"),
                                obj.getBoolean("is_accepted"),
                                comment
                            )
                        )
                        if(obj.getBoolean("is_accepted")){
                            acceptedIndex = i
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                // Binds the obtained data to the RecyclerView
                // Assigns Linear Layout as the layout manager for the RecyclerView
                val adapter = AnswerAdapter(answers)
                adapter.onItemClick = { answer, index ->
                    // Get to new page here
                    if(answer.isAccepted){
                        feedback.text = "Correct!"
                        feedback.setTextColor(Color.parseColor("#00FF00"))
                    } else {
                        feedback.text = "Incorrect!"
                        feedback.setTextColor(Color.parseColor("#FF0000"))
                        var correctView = layoutManager.findViewByPosition(acceptedIndex)
                        correctView?.setBackgroundColor(Color.parseColor("#00FF00"))
                    }
                }
                answerList.adapter = adapter
                val dividerItemDecoration = DividerItemDecoration(
                    answerList.getContext(),
                    layoutManager.getOrientation()
                )

                // Displays dividers
                answerList.addItemDecoration(dividerItemDecoration)
            },
            { error ->
                println("===============")
                println(error)
            }
        )
        queue.add(request)
    }
}