package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val question: QuestionX,
    val questionType: String,
    val sort: Int
)