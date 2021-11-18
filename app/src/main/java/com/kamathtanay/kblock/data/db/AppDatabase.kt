package com.kamathtanay.kblock.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kamathtanay.kblock.data.dao.CallLogDao
import com.kamathtanay.kblock.data.dao.ContactDao
import com.kamathtanay.kblock.data.db.entity.CallLog
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.db.entity.typeconverter.Converters

@Database(entities = [Contact::class, CallLog::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao
    abstract fun getCallLogDao(): CallLogDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "KBlockDatabase.db"
            ).build()
    }
}