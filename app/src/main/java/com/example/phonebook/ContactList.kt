package com.example.phonebook

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.models.ContactListAdapter
import com.example.phonebook.models.SwipeToRemoveGesture
import com.example.phonebook.viewmodels.ContactViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ContactCardList : Fragment() {
    private val contactViewModel: ContactViewModel by activityViewModels()
    private lateinit var adapter: ContactListAdapter
    private lateinit var binding: FragmentContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val isTablet = resources.getBoolean(R.bool.isTablet)
        binding = DataBindingUtil.inflate<FragmentContactListBinding>(inflater,
            R.layout.fragment_contact_list,
            container,
            false)
        binding.lifecycleOwner = this

        // Creates an instance of ContactViewModel adapted for navGraph
        adapter = ContactListAdapter(requireContext())
        binding.contactCardList.adapter = adapter

        val swipeGesture = SwipeToRemoveGesture(binding.contactCardList, adapter, contactViewModel)
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.contactCardList)

        // Layout manager and adapter are bound to RecyclerView
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.contactCardList.layoutManager = LinearLayoutManager(context)
        else
            binding.contactCardList.layoutManager = GridLayoutManager(context, 2)

        // Info Fragment displays on screen only on tablets
        if (isTablet)
            binding.infoFragment.visibility = View.VISIBLE
        else
            binding.infoFragment.visibility = View.GONE

        contactViewModel.contactListIsEmptyModel.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            // Displays msg on screen if cardList is empty
            if (it) {
                binding.emptyListTv.visibility = View.VISIBLE
                binding.infoFragment.visibility = View.GONE
            } else {
                binding.emptyListTv.visibility = View.GONE
            }
        })
        contactViewModel.contactListModel.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.updateList(it)
        })

        // When an item in the contact list is clicked we get its index and notify the ViewModel
        adapter.onItemClick = { _, viewHolder ->
            contactViewModel.setContactInfo(viewHolder.absoluteAdapterPosition)
            if (!isTablet)
                findNavController().navigate(R.id.action_contactCardList_to_contactInfo)
        }

        // Navigates to AddContact fragment when button is pressed
        binding.addContactButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_contactCardList_to_addContact)
        }

        return binding.root
    }
}