package app.loococo.domain.usecase

import app.loococo.domain.model.CalendarType
import app.loococo.domain.model.toCalendarType
import app.loococo.domain.model.toStringValue
import app.loococo.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    fun saveCalendarType(type: CalendarType) {
        preferencesRepository.saveCalendarType(type.toStringValue())
    }

    fun getCalendarType(): CalendarType {
        return preferencesRepository.getCalendarType().toCalendarType()
    }
}