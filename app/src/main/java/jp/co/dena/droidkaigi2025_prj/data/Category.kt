package jp.co.dena.droidkaigi2025_prj.data

data class Category(
    val id: Int,
    val items: List<Item>,
    val sort: Int,
    val title: Title
)