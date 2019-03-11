package ca.qc.mtl.mohaila.kotlinsoquestionsokhttp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import android.net.Uri

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var questions = ArrayList<Question>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleView.text = questions[position].title
    }

    fun setQuestions(items: ArrayList<Question>) {
        questions = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.title)
        val cardView = itemView.findViewById<CardView>(R.id.cardview)

        init {
            cardView.setOnClickListener {
                val question = questions[adapterPosition]
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(question.link))
                it.context.startActivity(intent)
            }
        }
    }
}