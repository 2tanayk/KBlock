package com.kamathtanay.kblock.data.contacts.data

data class ContactData(
    val id: String,
    val name: String,
) {
    var numbers = ArrayList<String>()
}
