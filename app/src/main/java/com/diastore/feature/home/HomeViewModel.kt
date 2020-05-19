package com.diastore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.Entry
import com.diastore.repo.EntriesRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val entriesRepository: EntriesRepository) : ViewModel() {
    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>>
        get() = _entries

    private val _isEntriesListRefreshing = MutableLiveData<Boolean>()
    val isEntriesListRefreshing: LiveData<Boolean>
        get() = _isEntriesListRefreshing

    init {
        getEntries()
    }

    fun getEntries() {
        viewModelScope.launch {
            _entries.value = entriesRepository.getAllEntries()
            _isEntriesListRefreshing.value = false
        }
    }

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch {
            val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
            tempEntries.removeIf {
                it.id == entry.id
            }
            entriesRepository.deleteEntry(entry.id)
            _entries.value = tempEntries
        }
    }

    fun handleSelectedEntryChange(selectedEntry: Entry) {
        viewModelScope.launch {
            val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
            val containsEntry = tempEntries.find { it.id == selectedEntry.id }
            if (containsEntry != null) {
                tempEntries.forEachIndexed { index, entry ->
                    if (entry.id == selectedEntry.id) {
                        entriesRepository.updateEntry(selectedEntry)
                        tempEntries[index] = selectedEntry
                        return@forEachIndexed
                    }
                }
            } else {
                entriesRepository.addEntry(selectedEntry)
                tempEntries.add(0, selectedEntry)
            }
            _entries.value = tempEntries.sortedDescending()
        }
    }
}