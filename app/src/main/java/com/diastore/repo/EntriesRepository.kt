package com.diastore.repo

import com.diastore.database.EntriesDao
import com.diastore.model.Entry
import com.diastore.service.EntriesService
import java.util.UUID

class EntriesRepository(
    private val entriesService: EntriesService,
    private val entriesDao: EntriesDao
) {

    suspend fun addEntry(userId: UUID, entry: Entry): Entry = entriesService.addEntry(userId, entry)

    suspend fun updateEntry(userId: UUID, entry: Entry): Entry = entriesService.updateEntry(userId, entry)

    suspend fun deleteEntry(entryId: UUID) {
        entriesService.deleteEntry(entryId)
    }

    suspend fun getAllEntries(userId: UUID) = entriesService.getUserEntries(userId)

    suspend fun addEntryToLocalDb(entry: Entry) {
        entriesDao.insert(entry)
    }

    suspend fun updateEntryInLocalDb(entry: Entry) {
        entriesDao.update(entry)
    }

    suspend fun deleteEntryFromLocalDb(entryId: UUID) {
        entriesDao.deleteEntryById(entryId.toString())
    }

    suspend fun getAllEntriesFromLocalDb() = entriesDao.getAllEntries()

    suspend fun insertAllEntries(entries: List<Entry>) {
        if (getAllEntriesFromLocalDb().isEmpty()) {
            entriesDao.insertAllEntries(entries)
        }
    }
}