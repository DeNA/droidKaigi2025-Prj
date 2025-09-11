package jp.co.dena.droidkaigi2025_prj.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: Name,
    val sort: Int
)