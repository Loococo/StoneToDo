package app.loococo.data.repository

import app.loococo.data.local.preferences.SharedPreferencesManager
import app.loococo.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesManager: SharedPreferencesManager
) : PreferencesRepository {

    override fun saveCalendarType(type: String) {
        preferencesManager.saveString("CALENDAR_TYPE", type)
    }

    override fun getCalendarType(): String? {
        return preferencesManager.getString("CALENDAR_TYPE")
    }
}