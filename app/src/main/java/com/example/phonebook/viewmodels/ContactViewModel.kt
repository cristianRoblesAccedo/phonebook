package com.example.phonebook.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact

class ContactViewModel: ViewModel() {
    val contactList = mutableListOf<Contact>()
    val contactListModel = MutableLiveData<MutableList<Contact>>()
    val contactModel = MutableLiveData<Contact>()
    val contactListIsEmptyModel = MutableLiveData<Boolean>()

    init {
        contactListIsEmptyModel.postValue(contactList.size == 0)
    }

    fun addContact(contact: Contact) {
        contactList.add(contact)
        contactListModel.postValue(contactList)
        contactListIsEmptyModel.postValue(contactList.size == 0)
    }

    fun removeContact(index: Int) {
        contactList.removeAt(index)
        contactListModel.postValue(contactList)
        contactListIsEmptyModel.postValue(contactList.size == 0)
    }

    fun setContactInfo(index: Int) {
        contactModel.postValue(contactList[index])
    }
}