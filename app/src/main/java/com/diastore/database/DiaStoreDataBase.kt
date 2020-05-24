package com.diastore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diastore.model.Entry
import com.diastore.model.User

@Database(entities = [Entry::class, User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DiaStoreDataBase : RoomDatabase() {

    abstract fun entriesDao(): EntriesDao
    abstract fun usersDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: DiaStoreDataBase? = null

        fun getInstance(context: Context): DiaStoreDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaStoreDataBase::class.java,
                        "diastore_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}