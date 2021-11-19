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

    @Query("SELECT * FROM contact_table WHERE is_user_contact=:isUserContact ORDER BY contact_name ASC")
    fun getAllUserContacts(isUserContact: Boolean = true): LiveData<List<Contact>>

    @Query("UPDATE contact_table SET is_blocked=:isBlocked WHERE contact_phone_number=:contactPhoneNo")
    suspend fun updateOnContactBlockUnblock(isBlocked: Boolean, contactPhoneNo: String)

    @Query("SELECT * FROM contact_table WHERE is_blocked=:isBlocked ORDER BY contact_name ASC")
    fun getAllBlockedContacts(isBlocked: Boolean = true): LiveData<List<Contact>>

    @Query("UPDATE contact_table SET is_blocked=:isBlocked WHERE contact_phone_number=:contactPhoneNo")
    suspend fun unblockContact(isBlocked: Boolean = false, contactPhoneNo: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewBlockedNumber(contact: Contact)

    @Query("UPDATE contact_table SET is_blocked=:newOldIsBlocked WHERE is_blocked=:oldIsBlocked")
    suspend fun unblockAllContacts(oldIsBlocked: Boolean = true, newOldIsBlocked: Boolean = false)

    @Query("UPDATE contact_table SET contact_name=:contactName WHERE contact_phone_number=:contactPhoneNo")
    suspend fun updateUserContact(contactName: String, contactPhoneNo: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUserContact(contact: Contact)

}