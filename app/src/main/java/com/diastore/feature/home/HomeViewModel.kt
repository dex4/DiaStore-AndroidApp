package com.diastore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diastore.model.Entry
import com.diastore.model.MealTypeSpecifier
import com.diastore.model.MomentSpecifier
import org.threeten.bp.LocalDateTime

class HomeViewModel : ViewModel() {
    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>>
        get() = _entries

    init {
        val initEntries = mutableListOf<Entry>()
        repeat((1..3).count()) {
            initEntries.add(
                Entry(
                    bloodSugarLevel = 100 + it,
                    carbohydratesIntake = 45 + it,
                    insulinIntake = 1F + it,
                    entryTime = LocalDateTime.now(),
                    physicalActivityDuration = 45 + it,
                    mealTypeSpecifier = MealTypeSpecifier.LUNCH,
                    entryMomentSpecifier = MomentSpecifier.BEFORE_MEAL
                )
            )
        }
        _entries.value = initEntries.sortedDescending()
    }

    fun deleteEntry(entry: Entry) {
        val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
        tempEntries.removeIf {
            it.id == entry.id
        }
        _entries.value = tempEntries
    }

    fun handleSelectedEntryChange(selectedEntry: Entry) {
        val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
        val containsEntry = tempEntries.find { it.id == selectedEntry.id }
        if (containsEntry != null) {
            tempEntries.forEachIndexed { index, entry ->
                if (entry.id == selectedEntry.id) {
                    tempEntries[index] = selectedEntry
                    return@forEachIndexed
                }
            }
        } else {
            tempEntries.add(0, selectedEntry)
        }
        _entries.value = tempEntries.sortedDescending()
    }
}