package com.diastore.repo

import com.diastore.database.EntriesDao
import com.diastore.model.Entry
import java.util.UUID

class EntriesRepository(private val entriesDao: EntriesDao) {

    suspend fun addEntry(entry: Entry) {
        entriesDao.insert(entry)
    }

    suspend fun getAllEntries() = entriesDao.getAllEntries()

    suspend fun updateEntry(entry: Entry) {
        entriesDao.update(entry)
    }

    suspend fun deleteEntry(entryId: UUID) {
        entriesDao.deleteEntryById(entryId.toString())
    }

    suspend fun getEntryById(entryId: UUID) {
        entriesDao.getEntryById(entryId.toString())
    }
}