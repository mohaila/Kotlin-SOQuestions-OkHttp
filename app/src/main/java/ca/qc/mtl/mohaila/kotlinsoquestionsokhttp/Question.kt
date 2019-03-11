package ca.qc.mtl.mohaila.kotlinsoquestionsokhttp

class Question(val title: String, val link: String) {
    override fun toString(): String {
        return title
    }
}

class Questions(val items: ArrayList<Question>)