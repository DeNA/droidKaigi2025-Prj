package jp.co.dena.droidkaigi2025_prj.data

class FavoriteSessionRepository {
    private val favoriteSessionIds = mutableListOf<String>()
    fun loadFavoriteSessionIds(): List<String> = favoriteSessionIds
    fun addFavoriteSession(sessionId: String) {
        favoriteSessionIds.add(sessionId)
    }
    fun removeFavoriteSession(sessionId: String) {
        favoriteSessionIds.remove(sessionId)
    }
}