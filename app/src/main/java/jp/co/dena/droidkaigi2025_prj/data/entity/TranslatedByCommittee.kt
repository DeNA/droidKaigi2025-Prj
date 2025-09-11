package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TranslatedByCommittee(
    val en: Boolean,
    val ja: Boolean
)