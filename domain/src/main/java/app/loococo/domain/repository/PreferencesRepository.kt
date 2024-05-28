package app.loococo.domain.repository

interface PreferencesRepository {

    fun saveCalendarType(type: String)

    fun getCalendarType(): String?
}