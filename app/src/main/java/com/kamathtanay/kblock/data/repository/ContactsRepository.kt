package com.kamathtanay.kblock.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.kamathtanay.kblock.data.contacts.KBlockContactsApi
import com.kamathtanay.kblock.data.contacts.data.ContactData
import com.kamathtanay.kblock.data.dao.ContactDao
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.util.CoroutineUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers

class ContactsRepository(private val db: AppDatabase, private val contactsApi: KBlockContactsApi) {
    private lateinit var contactDao: ContactDao
    private lateinit var allContacts: LiveData<List<Contact>>

    init {
        contactDao = db.getContactDao()
        allContacts = contactDao.getAllUserContacts()
    }

    private suspend fun retrieveAllContacts(): ArrayList<ContactData> =
        withContext(Dispatchers.IO) {
            Log.e("retrieving contacts", "Started...")
            val contactsListDeferred = async { contactsApi.getPhoneContacts() }
            val contactNumbersDeferred = async { contactsApi.getContactNumbers() }
            Log.e("retrieving contacts", "doing...operations...")

            val contactsList = contactsListDeferred.await()
            val contactNumbers = contactNumbersDeferred.await()

            Log.e("retrieving contacts", "await over??")

            contactsList.forEach {
                contactNumbers[it.id]?.let { numbers ->
                    it.numbers = numbers
                }
            }
            contactsList
        }


    suspend fun importAllContacts() {
        Log.e("Getting contacts...", "Started...")
        val contactsList = retrieveAllContacts()
        val dbContactsList = ArrayList<Contact>()
        Log.e("Getting contacts...", "Done...combining")

        withContext(Dispatchers.Default) {
            val cleanedContactData = cleanContactData(contactsList)
            for ((key, value) in cleanedContactData) {
                dbContactsList.add(Contact(contactName = value, contactPhoneNumber = key))
            }
        }

        withContext(Dispatchers.IO) {
            contactDao.saveAllContacts(dbContactsList)
        }
        Log.e("Getting contacts...", "combined")
    }

    fun updateOnContactBlockUnblock(isBlocked: Boolean, contactPhoneNo: String) {
        CoroutineUtil.io {
            contactDao.updateOnContactBlockUnblock(isBlocked, contactPhoneNo)
        }
    }

    private suspend fun cleanContactData(contactsList: ArrayList<ContactData>): HashMap<String, String> {
        val contactsHashMap: HashMap<String, String> = HashMap()
        for (contact in contactsList) {
            for (number in contact.numbers) {
                val cleanNumber = number.filter { !it.isWhitespace() }
                if (!contactsHashMap.containsKey(cleanNumber)) {
                    contactsHashMap[cleanNumber] = contact.name
                }
            }
        }
        return contactsHashMap
    }

    fun getAllUserContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}