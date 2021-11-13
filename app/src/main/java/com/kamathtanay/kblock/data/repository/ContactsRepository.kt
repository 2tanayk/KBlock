package com.kamathtanay.kblock.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kamathtanay.kblock.data.contacts.KBlockContactsApi
import com.kamathtanay.kblock.data.contacts.data.ContactData
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.db.entity.Contact
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers

class ContactsRepository(private val db: AppDatabase, private val contactsApi: KBlockContactsApi) {

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

            //is it returning?
            contactsList
        }

    suspend fun getAllContacts(): LiveData<List<Contact>> {
        if (userContactsNotFetched()) {
            Log.e("Getting contacts...", "Started...")
            val dbContactsList = ArrayList<Contact>()
            val contactsList = retrieveAllContacts()
            Log.e("Getting contacts...", "Done...combining")

            withContext(Dispatchers.Default) {
                contactsList.forEach { contact ->
                    contact.numbers.forEach { n ->
                        dbContactsList.add(
                            Contact(
                                contactName = contact.name,
                                contactPhoneNumber = n
                            )
                        )
                    }
                }
            }

            withContext(Dispatchers.IO) {
                db.getContactDao().saveAllContacts(dbContactsList)
            }
            Log.e("Getting contacts...", "combined")
        }
        Log.e("Getting contacts...", "returning")
        //something wrong!?
        val allContacts=db.getContactDao().getAllContacts()
        Log.e("All Contacts", allContacts.value.toString())
        return allContacts
    }

    private fun userContactsNotFetched(): Boolean {
        return false
    }
}