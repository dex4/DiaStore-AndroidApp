package com.diastore.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diastore.model.Entry
import com.diastore.repo.EntriesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class HomeViewModel(private val entriesRepository: EntriesRepository) : ViewModel() {
    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>>
        get() = _entries

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isEntriesListRefreshing = MutableLiveData<Boolean>()
    val isEntriesListRefreshing: LiveData<Boolean>
        get() = _isEntriesListRefreshing

    fun getEntries(userId: UUID) {
        _isEntriesListRefreshing.value = true
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val entries = entriesRepository.getAllEntries(userId)
                    entriesRepository.insertAllEntries(entries)
                    _entries.value = entries.sortedDescending()
                    _isEntriesListRefreshing.value = false
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _error.value = e.message
                } finally {
                    _isEntriesListRefreshing.value = false
                }
            }
        }
    }

    fun deleteEntry(entry: Entry) {
        _isEntriesListRefreshing.value = true
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    entriesRepository.deleteEntry(entry.id)
                    entriesRepository.deleteEntryFromLocalDb(entry.id)
                    val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
                    tempEntries.removeIf {
                        it.id == entry.id
                    }
                    _entries.value = tempEntries
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _error.value = e.message
                } finally {
                    _isEntriesListRefreshing.value = false
                }
            }
        }
    }

    fun handleSelectedEntryChange(userId: UUID, selectedEntry: Entry) {
        _isEntriesListRefreshing.value = true
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    val tempEntries = _entries.value?.toMutableList() ?: mutableListOf()
                    val containsEntry = tempEntries.find { it.id == selectedEntry.id }
                    if (containsEntry != null) {
                        val updatedEntry = entriesRepository.updateEntry(userId, selectedEntry)
                        tempEntries.forEachIndexed { index, entry ->
                            if (entry.id == updatedEntry.id) {
                                entriesRepository.updateEntryInLocalDb(updatedEntry)
                                tempEntries[index] = updatedEntry
                                return@forEachIndexed
                            }
                        }
                    } else {
                        val addedEntry = entriesRepository.addEntry(userId, selectedEntry)
                        entriesRepository.addEntryToLocalDb(addedEntry)
                        tempEntries.add(0, addedEntry)
                    }
                    _entries.value = tempEntries.sortedDescending()
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _error.value = e.message
                } finally {
                    _isEntriesListRefreshing.value = false
                }
            }
        }
    }
}