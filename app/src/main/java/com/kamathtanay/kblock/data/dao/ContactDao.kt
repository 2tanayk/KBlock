package com.kamathtanay.kblock.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kamathtanay.kblock.data.db.entity.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllContacts(contacts: List<Contact>)

    @Query("SELECT * FROM contact_table ORDER BY contact_name ASC")
    fun getAllContacts(): LiveData<List<Contact>>
}