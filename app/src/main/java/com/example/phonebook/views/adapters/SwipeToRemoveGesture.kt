package com.example.phonebook.views.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.viewmodels.ContactViewModel
import com.google.android.material.snackbar.Snackbar

class SwipeToRemoveGesture(
    val recyclerView: RecyclerView,
    val adapter: ContactListAdapter,
    val contactViewModel: ContactViewModel): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val tmpContact = adapter.getItem(viewHolder.absoluteAdapterPosition)
        contactViewModel.removeContact(viewHolder.absoluteAdapterPosition)
        adapter.updateList(contactViewModel.contactListLiveData.value!!)
        Snackbar.make(recyclerView, "Contact deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                contactViewModel.addContact(tmpContact)
                adapter.updateList(contactViewModel.contactListLiveData.value!!)
            }
            .show()
    }
}