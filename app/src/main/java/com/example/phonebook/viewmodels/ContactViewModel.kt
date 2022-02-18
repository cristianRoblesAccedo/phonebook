package com.example.phonebook.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.models.Contact

class ContactViewModel: ViewModel() {
    val contactList = mutableListOf<Contact>()
    val contactListLiveData = MutableLiveData<MutableList<Contact>>()
    val contactInfoLiveData = MutableLiveData<Contact>()
    val contactTmpLiveData = MutableLiveData<Contact>()
    val contactListIsEmptyLiveData = MutableLiveData<Boolean>()
    val imageSelectedLiveData = MutableLiveData<Boolean>()

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
        contactInfoLiveData.postValue(contactList[index])
    }

    fun setContactTmp(contact: Contact) {
        contactTmpLiveData.postValue(contact)
    }

    fun setContactImage(postValue: Boolean) {
        imageSelectedLiveData.postValue(postValue)
    }
}