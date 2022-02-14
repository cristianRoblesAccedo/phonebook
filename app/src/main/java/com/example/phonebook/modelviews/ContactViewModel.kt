package com.example.phonebook.modelviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact

class ContactViewModel: ViewModel() {
    private val contactList = mutableListOf<Contact>()
    val contactListModel = MutableLiveData<MutableList<Contact>>()
    val contactModel = MutableLiveData<Contact>()

    fun addContact(contact: Contact) {
        contactList.add(contact)
        contactListModel.postValue(contactList)
    }

    fun removeContact(index: Int) {
        contactList.removeAt(index)
        contactListModel.postValue(contactList)
    }

    fun setContactInfo(index: Int) {
        contactModel.postValue(contactList[index])
    }
}