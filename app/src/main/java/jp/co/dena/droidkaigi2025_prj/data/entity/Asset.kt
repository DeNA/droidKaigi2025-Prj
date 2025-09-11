package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val slideUrl: String?,
    val videoUrl: String?,
)