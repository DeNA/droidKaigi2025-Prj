package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class I18nDesc(
    val en: String,
    val ja: String
)