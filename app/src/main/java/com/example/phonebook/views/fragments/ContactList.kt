package com.example.phonebook.views.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.models.Contact
import com.example.phonebook.models.TMDBService
import com.example.phonebook.views.adapters.ContactListAdapter
import com.example.phonebook.views.adapters.SwipeToRemoveGesture
import com.example.phonebook.viewmodels.ContactViewModel
import com.example.phonebook.viewmodels.RetrofitViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactCardList : Fragment() {
    private val contactViewModel: ContactViewModel by activityViewModels()
    private val retrofitViewModel: RetrofitViewModel by activityViewModels()
    private lateinit var adapter: ContactListAdapter
    private lateinit var binding: FragmentContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Checks whether the device is a tablet based on its minimum width (600dp)
        val isTablet = resources.getBoolean(R.bool.isTablet)
        var landscapeTablet = false

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentContactListBinding>(inflater,
            R.layout.fragment_contact_list,
            container,
            false)
        binding.lifecycleOwner = this

        // Gets popular people from API
        retrofitViewModel.getPopularPeople()

        // Layout manager and adapter are bound to RecyclerView
        adapter = ContactListAdapter(requireContext())
        binding.contactCardList.adapter = adapter

        // Creates and ItemTouchHelper and attaches it to the ContactList
        val swipeGesture = SwipeToRemoveGesture(binding.contactCardList, adapter, contactViewModel)
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.contactCardList)

        // Changes layoutManager based on device type and its orientation
        if (isTablet || resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.contactCardList.layoutManager = GridLayoutManager(context, 2)
            if (isTablet && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                landscapeTablet = true
        } else {
            binding.contactCardList.layoutManager = LinearLayoutManager(context)
        }
        // Renders infoFragment only on landscape mode tablets
        binding.infoFragment.visibility = if (landscapeTablet) View.VISIBLE else View.GONE

        // Observer that changes the visibility on views based on the size of the contact list
        contactViewModel.contactListIsEmptyModel.observe(viewLifecycleOwner, androidx.lifecycle.Observer { isEmpty: Boolean ->
            // Displays msg on screen if cardList is empty
            if (isEmpty) {
                binding.emptyListTv.visibility = View.VISIBLE
                binding.infoFragment.visibility = View.GONE
            } else {
                binding.emptyListTv.visibility = View.GONE
            }
        })
        // Observer that updates the contact list in RecyclerView's adapter based on the changes made on contactListModel
        contactViewModel.contactListModel.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.updateList(it)
        })

        // When an item in the contact list is clicked we get its index and notify the ViewModel
        adapter.onItemClick = { _, viewHolder ->
            contactViewModel.setContactInfo(viewHolder.absoluteAdapterPosition)
            if (!landscapeTablet)
                findNavController().navigate(R.id.action_contactCardList_to_contactInfo)
        }

        // Navigates to AddContact fragment when button is pressed
        binding.addContactButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_contactCardList_to_addContact)
        }

        return binding.root
    }
}