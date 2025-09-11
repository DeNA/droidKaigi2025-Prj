package jp.co.dena.droidkaigi2025_prj.data

data class Question(
    val id: Int,
    val question: QuestionX,
    val questionType: String,
    val sort: Int
)