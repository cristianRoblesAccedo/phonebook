package com.example.phonebook.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact

class ContactViewModel: ViewModel() {
    val contactList = mutableListOf<Contact>()
    val contactListLiveData = MutableLiveData<MutableList<Contact>>()
    val contactLiveData = MutableLiveData<Contact>()
    val contactListIsEmptyLiveData = MutableLiveData<Boolean>()

    init {
        contactListIsEmptyLiveData.postValue(contactList.size == 0)
    }

    fun addContact(contact: Contact) {
        contactList.add(contact)
        contactListLiveData.postValue(contactList)
        contactListIsEmptyLiveData.postValue(contactList.size == 0)
    }

    fun removeContact(index: Int) {
        contactList.removeAt(index)
        contactListLiveData.postValue(contactList)
        contactListIsEmptyLiveData.postValue(contactList.size == 0)
    }

    fun setContactInfo(index: Int) {
        contactLiveData.postValue(contactList[index])
    }

    fun setContactInfo(contact: Contact) {
        contactLiveData.postValue(contact)
    }
}