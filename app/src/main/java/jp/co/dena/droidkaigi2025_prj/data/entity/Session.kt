package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val asset: Asset,
    val description: String?,
    val endsAt: String,
    val i18nDesc: I18nDesc,
    val i18nTargetAudience: I18nTargetAudience,
    val id: String,
    val interpretationTarget: Boolean,
    val isPlenumSession: Boolean,
    val isServiceSession: Boolean,
    val language: String,
    val lengthInMinutes: Int,
    val levels: List<String>,
    val message: String?,
    val noShow: Boolean,
    val roomId: Int,
    val sessionCategoryItemId: Int,
    val sessionType: String,
    val speakers: List<String>,
    val startsAt: String,
    val targetAudience: String,
    val title: Title,
    val translatedByCommittee: TranslatedByCommittee
)