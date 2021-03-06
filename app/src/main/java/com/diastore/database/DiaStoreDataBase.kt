package com.diastore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diastore.model.EncryptedUser
import com.diastore.model.Entry

@Database(entities = [Entry::class, EncryptedUser::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DiaStoreDataBase : RoomDatabase() {

    abstract fun entriesDao(): EntriesDao
    abstract fun encryptedUserDao(): EncryptedUserDao

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