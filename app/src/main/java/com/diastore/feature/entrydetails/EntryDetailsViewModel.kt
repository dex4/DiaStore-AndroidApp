package com.diastore.feature.entrydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.diastore.model.Entry
import com.diastore.model.MealTypeSpecifier
import com.diastore.model.MomentSpecifier
import org.threeten.bp.LocalDateTime
import java.util.UUID

class EntryDetailsViewModel : ViewModel() {
    private val _entry = MutableLiveData<Entry>()
    val entry: LiveData<Entry>
        get() = _entry

    private val _updatedEntry = MutableLiveData<Entry>()
    val updatedEntry: LiveData<Entry>
        get() = _updatedEntry

    val bloodSugarLevel = MutableLiveData("")
    val carbohydratesIntake = MutableLiveData("")
    val insulinIntake = MutableLiveData("")
    val physicalActivityDuration = MutableLiveData("")
    val entryTime = MutableLiveData<LocalDateTime>()
    val entryMomentSpecifier = MutableLiveData<MomentSpecifier>()
    val mealTypeSpecifier = MutableLiveData<MealTypeSpecifier>()
    val isDoneButtonEnabled: LiveData<Boolean> = Transformations.map(bloodSugarLevel) {
        !it.isNullOrEmpty()
    }

    fun setSelectedEntry(entry: Entry?) {
        _entry.value = entry

        bloodSugarLevel.value = _entry.value?.bloodSugarLevel?.toString() ?: ""
        carbohydratesIntake.value = _entry.value?.carbohydratesIntake?.toString() ?: ""
        insulinIntake.value = _entry.value?.insulinIntake?.toString() ?: ""
        physicalActivityDuration.value = _entry.value?.physicalActivityDuration?.toString() ?: ""
        entryTime.value = _entry.value?.entryTime ?: LocalDateTime.now()
        entryMomentSpecifier.value = _entry.value?.entryMomentSpecifier
        mealTypeSpecifier.value = _entry.value?.mealTypeSpecifier
    }

    fun updateEntry() {
        this._updatedEntry.value = Entry(
            id = entry.value?.id ?: UUID.randomUUID(),
            bloodSugarLevel = bloodSugarLevel.value?.toIntOrNull() ?: 0,
            carbohydratesIntake = carbohydratesIntake.value?.toIntOrNull() ?: 0,
            insulinIntake = insulinIntake.value?.toFloatOrNull() ?: 0f,
            physicalActivityDuration = physicalActivityDuration.value?.toIntOrNull() ?: 0,
            entryTime = entryTime.value ?: LocalDateTime.now(),
            mealTypeSpecifier = mealTypeSpecifier.value,
            entryMomentSpecifier = entryMomentSpecifier.value,
            hasDonePhysicalActivity = !physicalActivityDuration.value.isNullOrEmpty()
        )
    }
}