package jp.co.dena.droidkaigi2025_prj.data

import jp.co.dena.droidkaigi2025_prj.data.Languages
import javax.inject.Inject
import javax.inject.Singleton

interface IUserRepository {

    fun getLanguage(): Languages
    fun changeLanguage(language: Languages)
}

@Singleton
class UserRepository @Inject constructor() : IUserRepository {
    private var lang = Languages.ENGLISH

    override fun getLanguage(): Languages = lang

    override fun changeLanguage(language: Languages) {
        lang = language
    }
}