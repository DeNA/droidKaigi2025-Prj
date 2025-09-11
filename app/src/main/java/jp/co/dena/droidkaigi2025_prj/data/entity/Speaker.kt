package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val bio: String,
    val firstName: String,
    val fullName: String,
    val id: String,
    val isTopSpeaker: Boolean,
    val lastName: String,
    val profilePicture: String,
    val sessions: List<String>,
    val tagLine: String
)