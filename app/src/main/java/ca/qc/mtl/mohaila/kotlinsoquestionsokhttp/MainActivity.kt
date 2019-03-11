package ca.qc.mtl.mohaila.kotlinsoquestionsokhttp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = Adapter()
        recyclerview.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        getQuestions()
    }

    private fun getQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(BASE_URL).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val stream = response.body().charStream()
                    val reader = BufferedReader(stream)
                    val questions = Gson().fromJson(reader, Questions::class.java)
                    reader.close()

                    CoroutineScope(Dispatchers.Main).launch {
                        val adapter = recyclerview.adapter as Adapter
                        adapter.setQuestions(questions.items)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MainActivity, response.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val BASE_URL =
            "https://api.stackexchange.com/2.1/questions?order=desc&sort=creation&site=stackoverflow&tagged=kotlin"
    }
}
