package com.kamathtanay.kblock.data.repository

import androidx.lifecycle.LiveData
import com.kamathtanay.kblock.data.dao.ContactDao
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.util.CoroutineUtil

class BlockedRepository(private val db: AppDatabase) {
    private var contactDao: ContactDao
    private var allBlockedContacts: LiveData<List<Contact>>

    init {
        contactDao = db.getContactDao()
        allBlockedContacts = contactDao.getAllBlockedContacts()
    }

    fun getAllBlockedContacts(): LiveData<List<Contact>> {
        return allBlockedContacts
    }

    fun unblockContact(isBlocked: Boolean = false, contactPhoneNo: String){
        CoroutineUtil.io {
            contactDao.unblockContact(isBlocked,contactPhoneNo)
        }
    }

    fun insertNewBlockedNumber(contact: Contact) {
        CoroutineUtil.io {
            contactDao.insertNewBlockedNumber(contact)
        }
    }

    fun unblockAllContacts() {
        CoroutineUtil.io {
            contactDao.unblockAllContacts()
        }
    }

}